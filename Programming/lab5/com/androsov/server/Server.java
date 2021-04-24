package com.androsov.server;

import com.androsov.server.InternetConnection.IOHandler;
import com.androsov.server.InternetConnection.ServerIO;
import com.androsov.server.productManagment.ListProductManager;
import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.exceptions.ContentException;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {

        ServerIO io = new IOHandler();

        try {
            ProductManager manager = new ListProductManager("LAB5_CONTENT", io);
            boolean stop = false;
            while(!stop) {
                String response;
                String commandLine;

                try {
                    commandLine = io.getCommandLine();

                    if(!commandLine.equals("STOP_SERVER")) {
                        System.out.println("Got command: " + commandLine);
                        response = manager.getCommandHandler().executeCommand(commandLine);
                        io.sendResponse(response);
                    } else {
                        stop = true;
                    }
                } catch (IOException e) {
                    System.out.println("Server io exception: " + e.getMessage());
                    io.accept();
                }
            }
        } catch (IOException e) {
            System.out.println("No such env LAB5_CONTENT!");
        } catch (ContentException e) {
            System.out.println("Content exception: " + e.getMessage());
        }
    }
}
