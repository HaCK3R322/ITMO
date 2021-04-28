package com.androsov.server.productManagment;

import com.androsov.server.lab5Plains.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ListSerializer {
    public static void serializeListToFile(File file, List<Product> list) throws IOException, NullPointerException {
        try {
            FileOutputStream fostream = new FileOutputStream(file, false);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            ProductBuilder.ProductImitator[] productImitatorArray = new ProductBuilder.ProductImitator[list.size()];

            for (int i = 0; i < list.size(); i++) {
                productImitatorArray[i] = new ProductBuilder.ProductImitator();
                productImitatorArray[i].name = list.get(i).getName();
                productImitatorArray[i].coordinates = list.get(i).getCoordinates();
                productImitatorArray[i].price = list.get(i).getPrice();
                productImitatorArray[i].partNumber = list.get(i).getPartNumber();
                productImitatorArray[i].manufactureCost = list.get(i).getManufactureCost();
                productImitatorArray[i].unitOfMeasure = list.get(i).getUnitOfMeasure();
                productImitatorArray[i].owner = list.get(i).getOwner();
            }

            String JSONFormatString = gson.toJson(productImitatorArray);

            fostream.write(JSONFormatString.getBytes());

            fostream.close();
        } catch (Exception e) {
            throw e;
        }
    }
}
