package com.androsov.server;

import com.androsov.general.IO.IO;
import com.androsov.general.ObjectSerialization;
import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.server.commandMagment.CommandHandler;
import com.androsov.server.commandMagment.commands.*;
import com.androsov.server.internetConnection.ServerIO;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.productManagment.ListDeserializer;
import com.androsov.server.productManagment.ProductBuilder;

import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Server {
    public static void main(String[] args) throws IOException {
        final String PRODUCT_LIST_ENV = "LAB5_CONTENT";
        File lab5ContentFile;

        ServerIO serverIO = new ServerIO();

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
        commandHandler.init(list, messengersHandler, productBuilder, lab5ContentFile, initializationTime);

        LinkedList<Request> listOfRequests = new LinkedList<>();
        LinkedList<Response> listOfResponses = new LinkedList<>();

        class ResponseExecutor implements Runnable {
            final Request request;

            public ResponseExecutor(Request request) {
                this.request = request;
            }

            @Override
            public void run() {
                final Response response = commandHandler.executeCommand(request);
                listOfResponses.add(response);
            }
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            try {
                serverIO.acceptAll();

                while(serverIO.hasRequest()) {
                    final Request request = serverIO.get();
                    if(request != null) {
                       listOfRequests.add(request);
                    }
                }

                //обрабатываем реквесты, создаем респонсы, удаляем обработанные реквесты
                //TODO вынести это в отдельный поток
                for (Request request : listOfRequests) {
                    listOfRequests.remove(request);
                    executorService.execute(new ResponseExecutor(request));
                }

                for(Response response : listOfResponses) {
                    Thread sendThread = new Thread(() -> serverIO.send(response));
                    sendThread.start();
                    listOfResponses.remove(response);
                }

            } catch (StreamCorruptedException ignored) {

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static class RequestGetterThread implements Runnable {
        final LinkedList<Request> listOfRequests;
        final Request request;
        final ServerIO serverIO;

        public RequestGetterThread(LinkedList<Request> listOfRequests, Request request, ServerIO serverIO) {
            this.listOfRequests = listOfRequests;
            this.request = request;
            this.serverIO = serverIO;
        }

        @Override
        public void run() {
            final Request request = serverIO.get();
            if(request != null) {
                listOfRequests.add(request);
            }
        }
    }
}

