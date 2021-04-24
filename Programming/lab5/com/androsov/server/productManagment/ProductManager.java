package com.androsov.server.productManagment;

import com.androsov.server.CommandMagment.CommandHandler;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.exceptions.ContentException;
import com.androsov.server.productManagment.exceptions.ManagmentException;

import java.io.IOException;

public interface ProductManager {
    String executeCommand(String name, String[] args);

    ProductBuilder getProductBuilder();
    CommandHandler getCommandHandler();
    int getSize();
    Product getProduct(long id);

    void deserialize() throws IOException, ContentException;
    void serialize() throws IOException;
    String getProductsInfo();
    String getManagerInfo();
    Product getProductById(long id);
    boolean productExist(long id);
    void add(Product p) throws ContentException;
    void update(long id, Product p);
    void removeById(long id) throws ManagmentException;
    void clear();
    void removeFirst() throws ManagmentException;
    void sort();
    void removeAnyByManufactureCost(Float manufactureCost) throws ManagmentException;
    Float averageOfManufactureCost();
    int countByPrice(Integer price);
}
