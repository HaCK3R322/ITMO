package com.androsov.server;

import com.androsov.general.IO.IO;
import com.androsov.general.ObjectSerialization;
import com.androsov.general.request.Request;
import com.androsov.general.request.RequestImpl;
import com.androsov.general.response.Response;
import com.androsov.server.commandMagment.CommandHandler;
import com.androsov.server.commandMagment.commands.*;
import com.androsov.server.internetConnection.AsyncIOHandler;
import com.androsov.server.internetConnection.ServerIO;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.productManagment.ListDeserializer;
import com.androsov.server.productManagment.ProductBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        final String PRODUCT_LIST_ENV = "LAB5_CONTENT";
        File lab5ContentFile;

        ServerIO serverIO = new ServerIO();
        IO io = serverIO;

        ProductBuilder productBuilder = new ProductBuilder();
        ListDeserializer deserializer = new ListDeserializer(productBuilder);
        List<Product> list;
        LocalDateTime initializationTime;
        try {
            lab5ContentFile = new File(System.getenv(PRODUCT_LIST_ENV));
            if(!lab5ContentFile.createNewFile()) {
                list = deserializer.deserializeFromFile(lab5ContentFile);
            } else {
                list = new LinkedList<>();
            }
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
        commandHandler.registryCommand(new Add(list, productBuilder, messengersHandler));
        commandHandler.registryCommand(new AverageOfManufactureCost(list, messengersHandler));
        commandHandler.registryCommand(new Clear(list, messengersHandler));
        commandHandler.registryCommand(new CountByPrice(list, messengersHandler));
        commandHandler.registryCommand(new ExecuteScript(commandHandler, messengersHandler));
        commandHandler.registryCommand(new Help(commandHandler, messengersHandler));
        commandHandler.registryCommand(new History(commandHandler, messengersHandler));
        commandHandler.registryCommand(new Info(list, initializationTime, messengersHandler));
        commandHandler.registryCommand(new RemoveById(list, messengersHandler));
        commandHandler.registryCommand(new RemoveByManufactureCost(list, messengersHandler));
        commandHandler.registryCommand(new RemoveFirst(list, messengersHandler));
        commandHandler.registryCommand(new Save(list, lab5ContentFile, messengersHandler));
        commandHandler.registryCommand(new Show(list, messengersHandler));
        commandHandler.registryCommand(new Sort(list, messengersHandler));
        commandHandler.registryCommand(new UpdateById(list, productBuilder, messengersHandler));
        commandHandler.registryCommand(new Exit(messengersHandler));

        commandHandler.registryCommand(new GetCommandsFormats(commandHandler));

        while (true) {
            try {
                while (serverIO.hasRequest()) {
                    final Request request = (Request) ObjectSerialization.deserialize(io.get());
                    serverIO.setUser(request.getUser());
                    final Response response = commandHandler.executeCommand(request);
                    io.send(ObjectSerialization.serialize(response));
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

//        String commandLine;
//        while (true) {
//            try {
//                asyncIO.selector.select();
//                asyncIO.configureKeys();
//
//                while (serverIO.hasRequest()) {
//                    final Request request = (Request) ObjectSerialization.deserialize(io.get());
//                    final Response response = commandHandler.executeCommand(request);
//                    io.send(ObjectSerialization.serialize(response));
//                }
//
//
//                //key may be "acceptable" or "readable"
//                while (asyncIO.goToNextKey()) {
//                    asyncIO.accept();
//                    commandLine = asyncIO.getCommandLine();
//                    if(!commandLine.equals("wait") && !commandLine.equals("")) {
//                        System.out.println("Got command line from client (" + asyncIO.getCurrentSocketAddress() + "): " + commandLine);
//                        //asyncIO.sendResponse(commandHandler.executeCommand(commandLine));
//                    }
//                    asyncIO.removeCurrentKey();
//                }
//            } catch (IOException e) {
//                System.out.println("Server (in work with " + asyncIO.getCurrentSocketAddress() + ") in cycle exception: " + e.getMessage());
//                asyncIO.closeCurrentChannel();
//            }
//        }

        //asyncIO.close();
        //commandHandler.executeCommand("save");
    }
}