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
    public ProductBuilder() {
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

    public Product buildProduct(String name,
                                Coordinates coordinates,
                                Integer price,
                                String partNumber,
                                Float manufactureCost,
                                UnitOfMeasure unitOfMeasure,
                                Person owner) throws ContentException {
        Product product;

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

        return product;
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
        ProductValidator.validateProduct(id, name, coordinates, creationDate, price, partNumber, usedPartNumbers, manufactureCost, unitOfMeasure, owner);

        idToAssign++;
        usedPartNumbers.add(partNumber);

        return new Product(id, name, coordinates, creationDate, price, partNumber, manufactureCost, unitOfMeasure, owner);
    }

    public Product buildProduct(ProductImitator productImitator, long id) throws ContentException {
        Product product;

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

        return product;
    }

    public Product buildProduct(ProductImitator productImitator) throws ContentException {
        Product product;

        product = buildProduct(productImitator, idToAssign);

        return product;
    }
}
