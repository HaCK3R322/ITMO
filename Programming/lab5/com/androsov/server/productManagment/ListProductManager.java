package com.androsov.server.productManagment;

import com.androsov.server.CommandMagment.CommandHandler;
import com.androsov.server.InternetConnection.ServerIO;
import com.androsov.server.productManagment.ProductManagerCommands.*;
import com.androsov.server.productManagment.exceptions.ContentException;
import com.androsov.server.lab5Plains.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import com.androsov.server.productManagment.exceptions.ManagementException;

public class ListProductManager implements ProductManager{

    public List<Product> list;
    public LocalDateTime initDate;
    public File file;
    public ProductBuilder productBuilder;
    public CommandHandler commandHandler;

    /**
     * realization of ListProductManager for LinkedList collection and prepared for work
     */
    public ListProductManager(String filePathEnv, ServerIO io) throws IOException, ContentException {
        list = new LinkedList<>();
        initDate = LocalDateTime.now();

        productBuilder = new ProductBuilder();

        commandHandler = new CommandHandler();
        commandHandler.registryCommand(new Help(this));
        commandHandler.registryCommand(new Info(this));
        commandHandler.registryCommand(new Show(this));
        commandHandler.registryCommand(new Add(this, io));
        commandHandler.registryCommand(new UpdateById(this, io));
        commandHandler.registryCommand(new RemoveById(this));
        commandHandler.registryCommand(new Clear(this));
        commandHandler.registryCommand(new Save(this));
        commandHandler.registryCommand(new ExecuteScript(this));
        commandHandler.registryCommand(new RemoveFirst(this));
        commandHandler.registryCommand(new Sort(this));
        commandHandler.registryCommand(new History(this));
        commandHandler.registryCommand(new RemoveByManufactureCost(this));
        commandHandler.registryCommand(new AverageOfManufactureCost(this));
        commandHandler.registryCommand(new CountByPrice(this));

        file = new File(System.getenv(filePathEnv));
        deserialize();
        System.out.println("List was deserialized from " + filePathEnv + ": " + System.getenv(filePathEnv));
    }

    @Override
    public String executeCommand(String name, String[] args) {
        return commandHandler.executeCommand(name, args);
    }

    @Override
    public ProductBuilder getProductBuilder() { return productBuilder; }
    @Override
    public CommandHandler getCommandHandler() { return commandHandler; }
    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Product getProduct(long id) {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getId() == id)
                return list.get(i);
        }

        return null;
    }

    @Override
    public void deserialize() throws IOException, ContentException {
        ListDeserializer deserializer = new ListDeserializer(productBuilder);
        list = deserializer.deserializeFromFile(file);
    }

    @Override
    public void serialize() throws IOException {
        ListSerializer serializer = new ListSerializer();
        serializer.serializeListToFile(file, list);
    }

    @Override
    public String getProductsInfo() {
        String info = "";

        for(int i = 0; i < list.size(); i++) {
            info += list.get(i).toString();
            if(i != list.size() - 1)
                info += "\n";
        }

        return info;
    }

    @Override
    public String getManagerInfo() {
        return ("Collection info:" + "\n" +
                "   type: LinkedList" + "\n" +
                "   initialization date: " + initDate.toString() + "\n" +
                "   number of elements: " + list.size()
        );
    }

    @Override
    public Product getProductById(long id) {
        Product product = null;
        for(int i = 0; i < list.size(); i++) {
            if(id == list.get(i).getId())
                product = list.get(i);
        }
        return product;
    }

    @Override
    public boolean productExist(long id) {
        boolean exist = false;

        for(int i = 0; i < list.size(); i++) {
            if (id == list.get(i).getId()) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    @Override
    public void add(Product p) throws ContentException {
        if(p != null) {
            list.add(p);
        } else {
            throw new ContentException("Cant add product: Null pointer exception");
        }
    }

    @Override
    public void update(long id, Product p) throws NullPointerException {
        if(productExist(id)) {
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).getId() == id) {
                    list.set(i, p);
                }
            }
        } else {
            throw new NullPointerException("No product with such id");
        }
    }

    @Override
    public void removeById(long id) throws ManagementException {
        if(productExist(id)) {
            ListIterator<Product> iter = list.listIterator();
            Product p;
            while ((p = iter.next()) != null) {
                if(p.getId() == id) {
                    iter.remove();
                    productBuilder.usedPartNumbers.remove(p.getPartNumber());
                    break;
                }
            }
        } else {
            throw new ManagementException("No product with such id");
        }
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public void removeFirst() throws ManagementException {
        if(list.size() > 0) {
            list.remove(0);
        } else {
            throw new ManagementException("List is empty");
        }
    }

    @Override
    public void sort() {
        list.sort(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.compareTo(p2);
            }
        });
    }


    @Override
    public void removeAnyByManufactureCost(Float manufactureCost) throws ManagementException {
        boolean manufactureCost_exist = false;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getManufactureCost().equals(manufactureCost)) {
                list.remove(i);
                manufactureCost_exist = true;
            }
        }
        if(!manufactureCost_exist)
            throw new ManagementException("No product with such id");
    }

    @Override
    public Float averageOfManufactureCost() {
        float average = 0;
        for(int i = 0; i < list.size(); i++) {
            average += list.get(i).getManufactureCost();
        }
        average /= list.size();
        return average;
    }

    @Override
    public int countByPrice(Integer price) {
        int count = 0;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getPrice().intValue() == price.intValue())
                count++;
        }
        return count;
    }
}