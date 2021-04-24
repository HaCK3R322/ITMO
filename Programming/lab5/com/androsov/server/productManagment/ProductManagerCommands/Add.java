package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.InternetConnection.ServerIO;
import com.androsov.server.lab5Plains.*;
import com.androsov.server.productManagment.ListProductManager;
import com.androsov.server.productManagment.ProductBuilder;
import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;
import com.androsov.server.productManagment.exceptions.ContentException;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Add extends ProductManagerCommand {
    ServerIO io;

    public Add(ProductManager manager, ServerIO io) {
        super(manager);

        name = "add";
        description = "Manual step-by-step product creation.";
        this.io = io;
    }

    public String execute(String[] args) {
        String result = "wery weird";

        try {
            manager.add(manager.getProductBuilder().buildProduct(createProductManually(manager, this.io)));
            result = "Product was added.";
        } catch (IOException e) {
            System.out.println("io " + e.getMessage());
        } catch (ContentException e) {
            System.out.println("Content exception: " + e.getMessage());
        }

        return result;
    }

    public static ProductBuilder.ProductImitator createProductManually(ProductManager manager, ServerIO io) throws IOException, ContentException {
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
        io.sendResponse("Enter coodinate x:");
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

        io.sendResponse("Enter coodinate y:");
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
        List<String> usedPartNumbers = manager.getProductBuilder().usedPartNumbers;
        io.sendResponse("Enter part number:");
        statementNotPicked = true;
        while(statementNotPicked) {
            productPartNumber = io.getCommandLine();
            boolean partNumberAlreadyUsed = false;
            for(int i = 0; i < usedPartNumbers.size(); i++) {
                if(productPartNumber.equals(usedPartNumbers.get(i)))
                    partNumberAlreadyUsed = true;
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

//manager.add(manager.getProductBuilder().buildProduct(createProductManually()));

/*

public Product createProductManually(List<String> commandList, int currentCommandLine) throws ContentException {
        Product product = null;

        try {
            product = createProductManually(commandList, currentCommandLine, manager.getProductBuilder().idToAssign);
        } catch (ContentException e) {
            throw e;
        }

        return product;
    }

    public Product createProductManually(List<String> commandList, int currentCommandLine, long id) throws ContentException {
        Product product;

        currentCommandLine++;

        String productName;
        productName = commandList.get(currentCommandLine);
        if(productName.replaceAll("\\s", "").equals("") || productName == null) {
            throw new ContentException("Name is empty");
        }
        currentCommandLine++;

        double productX = 0; Double productY = null;
        try {
            productX = Double.parseDouble(commandList.get(currentCommandLine));
            if(productX > 653)
                throw new ContentException("X bigger than 653");
        } catch (NumberFormatException e) {
            throw new ContentException("x missing");
        }
        currentCommandLine++;
        try {
            productY = new Double(commandList.get(currentCommandLine));
            if(productY == null)
                throw new ContentException("y = null");
        } catch (NumberFormatException e) {
            throw new ContentException("missing y coordinate");
        }
        currentCommandLine++;

        Integer productPrice = null;
        try {
            productPrice = new Integer(commandList.get(currentCommandLine));
            if(productPrice == 0)
                throw new ContentException("price = 0");
        } catch (NumberFormatException e) {
            throw new ContentException("price missing");
        }
        currentCommandLine++;

        String productPartNumber = null;
        productPartNumber = commandList.get(currentCommandLine);
        boolean partNumberAlreadyUsed = false;
        for(int i = 0; i < usedPartNumbers.size(); i++) {
            if(productPartNumber.equals(usedPartNumbers.get(i)))
                partNumberAlreadyUsed = true;
        }
        if(productPartNumber.equals("") || productPartNumber == null || partNumberAlreadyUsed)
            throw new ContentException("part number is empty or already used");
        currentCommandLine++;


        Float productManufactureCost = null;
        try {
            productManufactureCost = new Float(commandList.get(currentCommandLine));
            if(productManufactureCost == null)
                throw new ContentException("manufacture cost is null");
        } catch (NumberFormatException e) {
            throw new ContentException("manufacture cost missing");
        }
        currentCommandLine++;

        UnitOfMeasure productUnitOfMeasure = null;
        try {
            productUnitOfMeasure = UnitOfMeasure.valueOf(commandList.get(currentCommandLine).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ContentException("Wrong unit of measure statment statement, try again");
        }
        currentCommandLine++;

        String ownerName = null;
        long ownerHeight = 0;
        Color ownerEyeColor = null;
        Color ownerHairColor = null;
        Country ownerNationality = null;

        ownerName = commandList.get(currentCommandLine);
        if(ownerName == null || ownerName.equals("")) {
            throw new ContentException("Wrong owner name statement, try again");
        }
        currentCommandLine++;

        try {
            ownerHeight = Long.parseLong(commandList.get(currentCommandLine));
            if(ownerHeight <= 0) {
                throw new ContentException("Wrong statement, try again");
            }
        } catch (NumberFormatException e) {
            throw new ContentException("Owner height missing");
        }
        currentCommandLine++;

        try {
            ownerEyeColor = Color.valueOf(commandList.get(currentCommandLine).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ContentException("owner eye color missing or not supported");
        }
        currentCommandLine++;

        try {
            ownerHairColor = Color.valueOf(commandList.get(currentCommandLine).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ContentException("owner hair color missing or not supported");
        }
        currentCommandLine++;

        try {
            ownerNationality = Country.valueOf(commandList.get(currentCommandLine).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ContentException("owner nationality missing or not supported");
        }

        try {
            product = new Product(id,
                    productName,
                    new Coordinates(productX, productY),
                    LocalDateTime.now(),
                    productPrice,
                    productPartNumber,
                    usedPartNumbers,
                    productManufactureCost,
                    productUnitOfMeasure,
                    new Person(ownerName, ownerHeight, ownerEyeColor, ownerHairColor, ownerNationality)
            );
            System.out.println("Product added successfully!\n----------");
        } catch (ContentException e) {
            throw e;
        }

        return product;
    }
 */
