package com.androsov;

import com.androsov.server.CommandMagment.CommandHandler;
import com.androsov.server.CommandMagment.Commands.*;
import com.androsov.server.InternetConnection.ServerIO;
import com.androsov.server.InternetConnection.SystemIOHandler;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.ListDeserializer;
import com.androsov.server.productManagment.ListSerializer;
import com.androsov.server.productManagment.ProductBuilder;

import java.io.File;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        final String PRODUCT_LIST_ENV = "LAB5_CONTENT";
        File lab5ContentFile;


        ServerIO sio = new SystemIOHandler();

        ProductBuilder productBuilder = new ProductBuilder();
        ListDeserializer deserializer = new ListDeserializer(productBuilder);
        ListSerializer serializer = new ListSerializer();
        List<Product> list;
        LocalDateTime initializationTime;
        try {
            lab5ContentFile = new File(System.getenv(PRODUCT_LIST_ENV));
            if(!lab5ContentFile.createNewFile()) {
                list = deserializer.deserializeFromFile(lab5ContentFile);
            } else {
                list = new LinkedList<>();
            }
        } catch (NullPointerException e) {
            System.out.println("List initialization error: Check your env vars.");
            lab5ContentFile = new File("lab5Content.json");
            list = new LinkedList<>();
        } catch (Exception e) {
            System.out.println("List initialization error: " + e.getMessage());
            lab5ContentFile = new File("lab5Content.json");
            list = new LinkedList<>();
        } finally {
            initializationTime = LocalDateTime.now();
        }

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registryCommand(new Add(list, productBuilder, sio));
        commandHandler.registryCommand(new AverageOfManufactureCost(list));
        commandHandler.registryCommand(new Clear(list));
        commandHandler.registryCommand(new CountByPrice(list));
        commandHandler.registryCommand(new ExecuteScript(list, commandHandler));
        commandHandler.registryCommand(new Help(list, commandHandler));
        commandHandler.registryCommand(new History(list, commandHandler));
        commandHandler.registryCommand(new Info(list, initializationTime));
        commandHandler.registryCommand(new RemoveById(list));
        commandHandler.registryCommand(new RemoveByManufactureCost(list));
        commandHandler.registryCommand(new RemoveFirst(list));
        commandHandler.registryCommand(new Save(list, lab5ContentFile));
        commandHandler.registryCommand(new Show(list));
        commandHandler.registryCommand(new Sort(list));
        commandHandler.registryCommand(new UpdateById(list, productBuilder, sio));

        String line;
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------------------------------------------");
        System.out.println(commandHandler.executeCommand("help"));
        System.out.println("-------------------------------------------------");
        System.out.println("Type command here:");
        boolean stop = false;
        while (!stop) {
            line = sc.nextLine();
            if(!line.equals("exit")) {
                System.out.println(commandHandler.executeCommand(line));
            } else {
                stop = true;
            }
        }
    }
}
