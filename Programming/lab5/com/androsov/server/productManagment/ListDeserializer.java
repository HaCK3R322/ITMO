package com.androsov.server.productManagment;

import com.androsov.server.lab5Plains.Coordinates;
import com.androsov.server.lab5Plains.Person;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.lab5Plains.UnitOfMeasure;
import com.androsov.server.productManagment.exceptions.ContentException;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ListDeserializer {
    private ProductBuilder productBuilder;

    public ListDeserializer(ProductBuilder productBuilder) {
        this.productBuilder = productBuilder;
    }

    public List<Product> deserealizeFromFile(File file) throws ContentException, NullPointerException, FileNotFoundException {
        Gson gson = new Gson();
        List<Product> productList = new LinkedList<>();

        try {
            ProductBuilder.ProductImitator[] ProductImitatorArray = gson.fromJson(new FileReader(file), ProductBuilder.ProductImitator[].class);

            for(int i = 0; i < ProductImitatorArray.length; i++) {
                try {
                    productList.add(productBuilder.buildProduct(ProductImitatorArray[i].name,
                            ProductImitatorArray[i].coordinates,
                            ProductImitatorArray[i].price,
                            ProductImitatorArray[i].partNumber,
                            ProductImitatorArray[i].manufactureCost,
                            ProductImitatorArray[i].unitOfMeasure,
                            ProductImitatorArray[i].owner)
                    );
                } catch (ContentException e) {
                    throw e;
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return productList;
    }
}
