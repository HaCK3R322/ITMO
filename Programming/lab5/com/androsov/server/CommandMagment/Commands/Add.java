package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.InternetConnection.ServerIO;
import com.androsov.server.lab5Plains.*;
import com.androsov.server.productManagment.ProductBuilder;
import com.androsov.server.productManagment.exceptions.ContentException;

import java.io.IOException;
import java.util.List;

public class Add extends ListCommand {
    ServerIO io;
    List<Product> list;
    ProductBuilder productBuilder;

    public Add(List<Product> list, ProductBuilder productBuilder, ServerIO io) {
        this.io = io;
        this.list = list;
        this.productBuilder = productBuilder;

        name = "add";
        description = "Manual step-by-step product creation.";
    }

    public String execute(String[] args) {
        String result = "very weird";

        try {
            list.add(productBuilder.buildProduct(createProductManually(productBuilder, this.io)));
            result = "Product was added.";
        } catch (IOException e) {
            System.out.println("io " + e.getMessage());
        } catch (ContentException e) {
            System.out.println("Content exception: " + e.getMessage());
        }

        return result;
    }

    public static ProductBuilder.ProductImitator createProductManually(ProductBuilder productBuilder, ServerIO io) throws IOException, ContentException {
        ProductBuilder.ProductImitator product = new ProductBuilder.ProductImitator();

        boolean statementNotPicked = true;

        String productName = null;
        io.sendResponse("Creation of new Product:\n----------\nenter name:");

        while(statementNotPicked) {
            productName = io.getCommandLine();
            if(productName.replaceAll("\\s", "").equals("") || productName == null)
                io.sendResponse("Name can not be empty. Try again:");
            else
                statementNotPicked = false;
        }

        double productX = 0; Double productY = null;
        statementNotPicked = true;
        io.sendResponse("Enter coordinate x:");
        while(statementNotPicked) {
            try {
                productX = Double.parseDouble(io.getCommandLine());
                if(productX > 653)
                    io.sendResponse("X coordinate can not be bigger than 653. Try again:");
                else
                    statementNotPicked = false;
            } catch (NumberFormatException e) {
                io.sendResponse("Please enter double-format number:");
            }
        }

        io.sendResponse("Enter coordinate y:");
        statementNotPicked = true;
        while(statementNotPicked) {
            try {
                productY = new Double(io.getCommandLine());
                if(productY == null)
                    io.sendResponse("y is NULL");
                else
                    statementNotPicked = false;
            } catch (NumberFormatException e) {
                io.sendResponse("Please enter double-format number:");
            }
        }

        Integer productPrice = null;
        io.sendResponse("Enter price:");
        statementNotPicked = true;
        while(statementNotPicked) {
            try {
                productPrice = new Integer(io.getCommandLine());
                if(productPrice == 0)
                    io.sendResponse("Price can not be 0! Try again:");
                else
                    statementNotPicked = false;
            } catch (NumberFormatException e) {
                io.sendResponse("Please enter int-format number:");
            }
        }

        String productPartNumber = null;
        List<String> usedPartNumbers = productBuilder.usedPartNumbers;
        io.sendResponse("Enter part number:");
        statementNotPicked = true;
        while(statementNotPicked) {
            productPartNumber = io.getCommandLine();
            boolean partNumberAlreadyUsed = false;
            for(int i = 0; i < usedPartNumbers.size(); i++) {
                if (productPartNumber.equals(usedPartNumbers.get(i))) {
                    partNumberAlreadyUsed = true;
                    break;
                }
            }
            if(productPartNumber.replaceAll("\\s", "").equals("") || productPartNumber == null || partNumberAlreadyUsed)
                io.sendResponse("Part number is empty or already used! Try again:");
            else
                statementNotPicked = false;
        }


        Float productManufactureCost = null;
        statementNotPicked = true;
        io.sendResponse("Enter manufacture cost:");
        while(statementNotPicked) {
            try {
                productManufactureCost = new Float(io.getCommandLine());
                if(productManufactureCost == null)
                    io.sendResponse("Manufacture cost can not be NULL. Try again:");
                else
                    statementNotPicked = false;
            } catch (NumberFormatException e) {
                io.sendResponse("Please enter float-format number:");
            }
        }

        UnitOfMeasure productUnitOfMeasure = null;
        io.sendResponse("enter unit of measure:");
        statementNotPicked = true;
        while(statementNotPicked) {
            try {
                productUnitOfMeasure = UnitOfMeasure.valueOf(io.getCommandLine().toUpperCase());
                statementNotPicked = false;
            } catch (IllegalArgumentException e) {
                io.sendResponse("That type of measure doesn't supports. Supported units: GRAMS, KILOGRAMS, SQUARE_METERS. Try again:");
            }
        }

        String ownerName = null;
        long ownerHeight = 0;
        Color ownerEyeColor = null;
        Color ownerHairColor = null;
        Country ownerNationality = null;

        io.sendResponse("Enter owners name:");
        statementNotPicked = true;
        while(statementNotPicked) {
            ownerName = io.getCommandLine();
            if(ownerName == null || ownerName.replaceAll("\\s", "").equals("")) {
                io.sendResponse("Owners name cant be empty. Try again:");
            } else {
                statementNotPicked = false;
            }
        }
        io.sendResponse("Enter owners height:");
        statementNotPicked = true;
        while(statementNotPicked) {
            try {
                ownerHeight = Long.parseLong(io.getCommandLine());
                if(ownerHeight <= 0) {
                    io.sendResponse("Owners height cant be zero or negative. Try again:");
                } else {
                    statementNotPicked = false;
                }
            } catch (NumberFormatException e) {
                io.sendResponse("Please enter long-format number:");
            }
        }
        io.sendResponse("Enter owner eye color:");
        statementNotPicked = true;
        while(statementNotPicked) {
            try {
                ownerEyeColor = Color.valueOf(io.getCommandLine().toUpperCase());
                statementNotPicked = false;
            } catch (IllegalArgumentException e) {
                io.sendResponse("That type of color doesn't supports. Supported colors: BLUE, GREEN, BLACK, ORANGE, WHITE, BROWN. Try again:");
            }
        }
        io.sendResponse("Enter owner hair color:");
        statementNotPicked = true;
        while(statementNotPicked) {
            try {
                ownerHairColor = Color.valueOf(io.getCommandLine().toUpperCase());
                statementNotPicked = false;
            } catch (IllegalArgumentException e) {
                io.sendResponse("Wrong statement, try again");
            }
        }
        io.sendResponse("Enter owner nationality:");
        statementNotPicked = true;
        while(statementNotPicked) {
            try {
                ownerNationality = Country.valueOf(io.getCommandLine().toUpperCase());
                statementNotPicked = false;
            } catch (IllegalArgumentException e) {
                io.sendResponse("Wrong statement, try again");
            }
        }

        product.name = productName;
        product.coordinates = new Coordinates(productX, productY);
        product.price = productPrice;
        product.partNumber = productPartNumber;
        product.manufactureCost = productManufactureCost;
        product.unitOfMeasure = productUnitOfMeasure;
        product.owner = new Person(ownerName, ownerHeight, ownerEyeColor, ownerHairColor, ownerNationality);

        return product;
    }
}