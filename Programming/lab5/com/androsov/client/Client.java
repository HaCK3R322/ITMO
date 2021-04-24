package com.androsov.client;

import com.androsov.client.InternetConnection.ClientIO;
import com.androsov.client.InternetConnection.IOHandler;
import com.androsov.client.userInterface.consoleUI.CommandLineInterface;

import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        ClientIO io = new IOHandler();
        try {
            String ipAddress = CommandLineInterface.askIp();
            int port = -1;
            try {
                port = Integer.parseInt(CommandLineInterface.askPort());
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Trying to get access to server opened on " + ipAddress + ":" + port);
            io.connectToServer(ipAddress, port);
            CommandLineInterface cli = new CommandLineInterface(io);
            cli.startListen();
        } catch (IOException e) {
            System.out.println("cannot connect to server: " + e.getMessage());
        }
    }
}
