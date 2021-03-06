package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.*;
import com.androsov.server.productManagment.ProductBuilder;
import com.androsov.server.productManagment.exceptions.ContentException;

import java.io.IOException;
import java.util.List;

public class Add extends ListCommand {
    final List<Product> list;
    final ProductBuilder productBuilder;
    final MessengersHandler messenger;

    public Add(List<Product> list, ProductBuilder productBuilder, MessengersHandler messenger) {
        this.list = list;
        this.productBuilder = productBuilder;
        this.messenger = messenger;

        name = "add";
        description = messenger.Add().description;
        argumentFormat = "String";
        userAccessible = true;
    }

    //решить: делать такой продукт на стороне клиента или на стороне сервера (в аргументах должны быть именно имитаторы)
    @Override
    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        Response response = new ResponseImpl(request.getUser());

        StringBuilder stringBuilder = new StringBuilder();
        for (Object object: args) {
            try {
                Product product = productBuilder.buildProduct((ProductBuilder.ProductImitator) object);
                list.add(product);
                stringBuilder.append("Product ");
                stringBuilder.append(product.getName());
                stringBuilder.append(" was added!");
            } catch (ContentException | ClassCastException e) {
                stringBuilder.append("\nProduct adding error: object ");
                stringBuilder.append(object.toString());
                stringBuilder.append(" can not be built: ");
                stringBuilder.append(e.getMessage());
            }
        }
        response.setMessage(stringBuilder.toString());
        return response;
    }

    /**
     * args:
     *0 - name
     *1 - coordinate x
     *2 - coordinate y
     *3 - price
     *4 - part number
     *5 - manufacture cost
     *6 - unit of measure
     *7 - owner name
     *8 - owner height
     *9 - owner eye color
     *10 - owner hair color
     *11 - nationality
     * @param args
     * @return
     */
    public String execute(String[] args) {
        String result = "";

        try {
            //list.add(productBuilder.buildProduct(createProductManually(productBuilder, this.io))); // not working in current time

            ProductBuilder.ProductImitator productImitator = createProductManually(args);

            list.add(productBuilder.buildProduct(productImitator));
            result = messenger.Add().product_was_added;
        } catch (ContentException e) {
            result = "Product build failed: " + e.getMessage();
            System.out.println("Content exception: " + e.getMessage());
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.Add().description;
    }

    public ProductBuilder.ProductImitator createProductManually(String[] args) throws ContentException {
        Person person;

        try {
            String name = args[7];
            long height = Long.parseLong(args[8]);
            Color eyeColor = Color.valueOf(args[9]);
            Color hairColor = Color.valueOf(args[10]);
            Country country = Country.valueOf(args[11]);

            person = new Person(name, height, eyeColor, hairColor, country);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new ContentException("Some owner states are wrong; Check your states;");
        }

        ProductBuilder.ProductImitator productImitator = new ProductBuilder.ProductImitator();

        try {
            productImitator.name = args[0];
            productImitator.coordinates = new Coordinates(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            productImitator.price = Integer.parseInt(args[3]);
            productImitator.partNumber = args[4];
            productImitator.manufactureCost = Float.parseFloat(args[5]);
            productImitator.unitOfMeasure = UnitOfMeasure.valueOf(args[6]);
            productImitator.owner = person;
        } catch (NumberFormatException | NullPointerException e) {
            throw new ContentException("Some states are wrong; Check your states;");
        }

        return productImitator;
    }

//    public ProductBuilder.ProductImitator createProductManually(ProductBuilder productBuilder, ServerIOOLD io) throws IOException, ContentException {
//        ProductBuilder.ProductImitator product = new ProductBuilder.ProductImitator();
//
//        boolean statementNotPicked = true;
//
//        String productName = null;
//        io.sendResponse( messenger.Add().Creation_of_new_product + "\n----------\n" + messenger.Add().enter_name);
//
//        while(statementNotPicked) {
//            productName = io.getCommandLine();
//            if(productName.replaceAll("\\s", "").equals("") || productName == null)
//                io.sendResponse(messenger.Add().Name_can_not_be_empty_try_again);
//            else
//                statementNotPicked = false;
//        }
//
//        double productX = 0; Double productY = null;
//        statementNotPicked = true;
//        io.sendResponse(messenger.Add().Enter_coordinate_x);
//        while(statementNotPicked) {
//            try {
//                productX = Double.parseDouble(io.getCommandLine());
//                if(productX > 653)
//                    io.sendResponse(messenger.Add().X_coordinate_can_not_be_bigger_than_653_Try_again);
//                else
//                    statementNotPicked = false;
//            } catch (NumberFormatException e) {
//                io.sendResponse(messenger.Add().Please_enter_double_format_number);
//            }
//        }
//
//        io.sendResponse(messenger.Add().Enter_coordinate_y);
//        statementNotPicked = true;
//        while(statementNotPicked) {
//            try {
//                productY = Double.parseDouble(io.getCommandLine());
//                if(productY == null)
//                    io.sendResponse(messenger.Add().y_is_NULL);
//                else
//                    statementNotPicked = false;
//            } catch (NumberFormatException e) {
//                io.sendResponse(messenger.Add().Please_enter_double_format_number);
//            }
//        }
//
//        Integer productPrice = null;
//        io.sendResponse(messenger.Add().Enter_price);
//        statementNotPicked = true;
//        while(statementNotPicked) {
//            try {
//                productPrice = Integer.parseInt(io.getCommandLine());
//                if(productPrice == 0)
//                    io.sendResponse(messenger.Add().Price_can_not_be_0_Try_again);
//                else
//                    statementNotPicked = false;
//            } catch (NumberFormatException e) {
//                io.sendResponse(messenger.Add().Please_enter_int_format_number);
//            }
//        }
//
//        String productPartNumber = null;
//        List<String> usedPartNumbers = productBuilder.usedPartNumbers;
//        io.sendResponse(messenger.Add().Enter_part_number);
//        statementNotPicked = true;
//        while(statementNotPicked) {
//            productPartNumber = io.getCommandLine();
//            boolean partNumberAlreadyUsed = false;
//            for (String usedPartNumber : usedPartNumbers) {
//                if (productPartNumber.equals(usedPartNumber)) {
//                    partNumberAlreadyUsed = true;
//                    break;
//                }
//            }
//            if(productPartNumber.replaceAll("\\s", "").equals("") || productPartNumber == null || partNumberAlreadyUsed)
//                io.sendResponse(messenger.Add().Part_number_is_empty_or_already_used_Try_again);
//            else
//                statementNotPicked = false;
//        }
//
//
//        Float productManufactureCost = null;
//        statementNotPicked = true;
//        io.sendResponse(messenger.Add().Enter_manufacture_cost);
//        while(statementNotPicked) {
//            try {
//                productManufactureCost = Float.parseFloat(io.getCommandLine());
//                if(productManufactureCost == null)
//                    io.sendResponse(messenger.Add().Manufacture_cost_can_not_be_NULL_Try_again);
//                else
//                    statementNotPicked = false;
//            } catch (NumberFormatException e) {
//                io.sendResponse(messenger.Add().Please_enter_float_format_number);
//            }
//        }
//
//        UnitOfMeasure productUnitOfMeasure = null;
//        io.sendResponse(messenger.Add().Enter_unit_of_measure);
//        statementNotPicked = true;
//        while(statementNotPicked) {
//            try {
//                productUnitOfMeasure = UnitOfMeasure.valueOf(io.getCommandLine().toUpperCase());
//                statementNotPicked = false;
//            } catch (IllegalArgumentException e) {
//                io.sendResponse(messenger.Add().That_type_of_measure_doesnt_supports_Supported_units_GRAMS_KILOGRAMS_SQUARE_METERS_Try_again);
//            }
//        }
//
//        String ownerName = null;
//        long ownerHeight = 0;
//        Color ownerEyeColor = null;
//        Color ownerHairColor = null;
//        Country ownerNationality = null;
//
//        io.sendResponse(messenger.Add().Enter_owners_name);
//        statementNotPicked = true;
//        while(statementNotPicked) {
//            ownerName = io.getCommandLine();
//            if(ownerName == null || ownerName.replaceAll("\\s", "").equals("")) {
//                io.sendResponse(messenger.Add().Owners_name_can_not_be_empty_Try_again);
//            } else {
//                statementNotPicked = false;
//            }
//        }
//        io.sendResponse(messenger.Add().Enter_owners_height);
//        statementNotPicked = true;
//        while(statementNotPicked) {
//            try {
//                ownerHeight = Long.parseLong(io.getCommandLine());
//                if(ownerHeight <= 0) {
//                    io.sendResponse(messenger.Add().Owners_height_cant_be_zero_or_negative_Try_again);
//                } else {
//                    statementNotPicked = false;
//                }
//            } catch (NumberFormatException e) {
//                io.sendResponse(messenger.Add().Please_enter_long_format_number);
//            }
//        }
//        io.sendResponse(messenger.Add().Enter_owner_eye_color);
//        statementNotPicked = true;
//        while(statementNotPicked) {
//            try {
//                ownerEyeColor = Color.valueOf(io.getCommandLine().toUpperCase());
//                statementNotPicked = false;
//            } catch (IllegalArgumentException e) {
//                io.sendResponse(messenger.Add().That_type_of_color_doesnt_supports_Supported_colors_BLUE_GREEN_BLACK_ORANGE_WHITE_BROWN_Try_again);
//            }
//        }
//        io.sendResponse(messenger.Add().Enter_owner_hair_color);
//        statementNotPicked = true;
//        while(statementNotPicked) {
//            try {
//                ownerHairColor = Color.valueOf(io.getCommandLine().toUpperCase());
//                statementNotPicked = false;
//            } catch (IllegalArgumentException e) {
//                io.sendResponse(messenger.Add().That_type_of_color_doesnt_supports_Supported_colors_BLUE_GREEN_BLACK_ORANGE_WHITE_BROWN_Try_again);
//            }
//        }
//        io.sendResponse(messenger.Add().Enter_owner_nationality);
//        statementNotPicked = true;
//        while(statementNotPicked) {
//            try {
//                ownerNationality = Country.valueOf(io.getCommandLine().toUpperCase());
//                statementNotPicked = false;
//            } catch (IllegalArgumentException e) {
//                io.sendResponse(messenger.Add().That_type_of_nationality_doesnt_supports_Supported_nationality_GERMANY_THAILAND_JAPAN_Try_again);
//            }
//        }
//
//        product.name = productName;
//        product.coordinates = new Coordinates(productX, productY);
//        product.price = productPrice;
//        product.partNumber = productPartNumber;
//        product.manufactureCost = productManufactureCost;
//        product.unitOfMeasure = productUnitOfMeasure;
//        product.owner = new Person(ownerName, ownerHeight, ownerEyeColor, ownerHairColor, ownerNationality);
//
//        return product;
//    }
}