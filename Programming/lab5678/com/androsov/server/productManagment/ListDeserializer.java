package com.androsov.server.productManagment;

import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.exceptions.ContentException;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class ListDeserializer {
    private ProductBuilder productBuilder;

    public ListDeserializer(ProductBuilder productBuilder) {
        this.productBuilder = productBuilder;
    }

    public final List<Product> deserializeFromFile(File file) throws ContentException, NullPointerException, FileNotFoundException {
        Gson gson = new Gson();
        List<Product> productList = new LinkedList<>();

        ProductBuilder.ProductImitator[] ProductImitatorArray = gson.fromJson(new FileReader(file), ProductBuilder.ProductImitator[].class);

        for (ProductBuilder.ProductImitator productImitator : ProductImitatorArray) {
            productList.add(productBuilder.buildProduct(productImitator.name,
                    productImitator.coordinates,
                    productImitator.price,
                    productImitator.partNumber,
                    productImitator.manufactureCost,
                    productImitator.unitOfMeasure,
                    productImitator.owner)
            );
        }

        return productList;
    }
}
