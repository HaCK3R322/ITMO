package com.androsov.server.productManagment;

import com.androsov.server.lab5Plains.Coordinates;
import com.androsov.server.lab5Plains.Person;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.lab5Plains.UnitOfMeasure;
import com.androsov.server.productManagment.exceptions.ContentException;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;



public class ProductBuilder {
    ProductBuilder() {
        idToAssign = 1;
        usedPartNumbers = new LinkedList<>();
    }

    public long idToAssign;
    public List<String> usedPartNumbers;

    public static class ProductImitator {
        public String name;
        public Coordinates coordinates;
        public Integer price;
        public String partNumber;
        public Float manufactureCost;
        public UnitOfMeasure unitOfMeasure;
        public Person owner;
    }

    public Product buildProduct(long id,
                       String name,
                       Coordinates coordinates,
                       java.time.LocalDateTime creationDate,
                       Integer price,
                       String partNumber,
                       List<String> usedPartNumbers,
                       Float manufactureCost,
                       UnitOfMeasure unitOfMeasure,
                       Person owner) throws ContentException {
        if(name.equals("") || name == null) throw new ContentException("Can not create product (id) " + id + " from file: name is empty");
        if(coordinates.getX() > (double)653) throw new ContentException("Can not create product " + name + " from file: x coordinate is bigger than 653");
        if(price == 0) throw new ContentException("Can not create product " + name + " from file: price == 0");
        boolean partNumberAlreadyUsed = false;
        for(int i = 0; i < usedPartNumbers.size(); i++) {
            if(partNumber.equals(usedPartNumbers.get(i))) {
                partNumberAlreadyUsed = true;
                break;
            }
        }
        if(partNumber.equals("") || partNumber == null || partNumberAlreadyUsed) throw new ContentException("Can not create product " + name + " from file: part number is empty or already used");
        if(manufactureCost == null) throw new ContentException("Can not create product " + name + " from file: manufacture cost == null");
        if(unitOfMeasure == null) throw new ContentException("Can not create product " + name + " from file: unit of measure == null");
        if(owner == null) throw new ContentException("Can not create product " + name + " from file: owner field empty");
        try {
            owner.CheckParams();
        } catch (ContentException e) {
            throw new ContentException("Can not create product " + name + " from file:" + e.getMessage());
        }

        idToAssign++;
        usedPartNumbers.add(partNumber);

        return new Product(id, name, coordinates, creationDate, price, partNumber, manufactureCost, unitOfMeasure, owner);
    }

    public Product buildProduct(String name,
                         Coordinates coordinates,
                         Integer price,
                         String partNumber,
                         Float manufactureCost,
                         UnitOfMeasure unitOfMeasure,
                         Person owner) throws ContentException {
        Product product = null;

        try {
            product = buildProduct(idToAssign,
                    name,
                    coordinates,
                    LocalDateTime.now(),
                    price,
                    partNumber,
                    usedPartNumbers,
                    manufactureCost,
                    unitOfMeasure,
                    owner);
        } catch (Exception e) {
            throw e;
        }

        return product;
    }

    public Product buildProduct(ProductImitator productImitator) throws ContentException {
        Product product = null;

        try {
            product = buildProduct(productImitator, idToAssign);
        } catch (Exception e) {
            throw new ContentException("Product-build content exception.");
        }

        return product;
    }

    public Product buildProduct(ProductImitator productImitator, long id) throws ContentException {
        Product product = null;

        try {
            product = buildProduct(id,
                    productImitator.name,
                    productImitator.coordinates,
                    LocalDateTime.now(),
                    productImitator.price,
                    productImitator.partNumber,
                    usedPartNumbers,
                    productImitator.manufactureCost,
                    productImitator.unitOfMeasure,
                    productImitator.owner);
        } catch (Exception e) {
            throw new ContentException("Product-build content exception.");
        }

        return product;
    }
}
