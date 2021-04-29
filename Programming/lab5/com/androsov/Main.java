package com.androsov;

import com.androsov.server.CommandMagment.CommandHandler;
import com.androsov.server.CommandMagment.Commands.*;
import com.androsov.server.InternetConnection.ServerIO;
import com.androsov.server.InternetConnection.SystemIOHandler;
import com.androsov.server.Messengers.MessengersHandler;
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

        MessengersHandler messengersHandler = new MessengersHandler();

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registryCommand(new ChangeLanguage(messengersHandler));
        commandHandler.registryCommand(new Add(list, productBuilder, sio, messengersHandler));
        commandHandler.registryCommand(new AverageOfManufactureCost(list, messengersHandler));
        commandHandler.registryCommand(new Clear(list, messengersHandler));
        commandHandler.registryCommand(new CountByPrice(list, messengersHandler));
        commandHandler.registryCommand(new ExecuteScript(list, commandHandler, messengersHandler));
        commandHandler.registryCommand(new Help(list, commandHandler, messengersHandler));
        commandHandler.registryCommand(new History(list, commandHandler, messengersHandler));
        commandHandler.registryCommand(new Info(list, initializationTime, messengersHandler));
        commandHandler.registryCommand(new RemoveById(list, messengersHandler));
        commandHandler.registryCommand(new RemoveByManufactureCost(list, messengersHandler));
        commandHandler.registryCommand(new RemoveFirst(list, messengersHandler));
        commandHandler.registryCommand(new Save(list, lab5ContentFile, messengersHandler));
        commandHandler.registryCommand(new Show(list, messengersHandler));
        commandHandler.registryCommand(new Sort(list, messengersHandler));
        commandHandler.registryCommand(new UpdateById(list, productBuilder, sio, messengersHandler));
        commandHandler.registryCommand(new Exit(messengersHandler));

        String line;
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------------------------------------------\n");
        System.out.println(commandHandler.executeCommand("help"));
        System.out.println("-------------------------------------------------");
        System.out.println("Type command here:");
        boolean stop = false;
        while (!stop) {
            line = sc.nextLine();
            String result = commandHandler.executeCommand(line);
            if(result != "\0")
                System.out.println(result);
            else
                stop = true;
        }
    }
}
