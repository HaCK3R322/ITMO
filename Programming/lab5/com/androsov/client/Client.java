package com.androsov.client;

import com.androsov.client.commandManagment.CommandValidator;
import com.androsov.client.internetConnection.ClientIO;
import com.androsov.client.internetConnection.IOHandler;
import com.androsov.client.messenger.EngMessenger;
import com.androsov.client.messenger.Messenger;
import com.androsov.client.userInterface.consoleUI.CommandLineInterface;

import java.io.IOException;
import java.util.Scanner;

//вариант 296173

public class Client {
    public static void main(String[] args) {
        Messenger messenger = new EngMessenger();
        init(messenger);
    }

    public static void init(Messenger messenger) {
        String serverAddress = CommandLineInterface.askAddress();
        try {
            init(serverAddress, messenger);
        } catch (IOException e) {
            System.out.println("Cannot connect to server: " + e.getMessage() + "\nTry again.");
            init(messenger);
        }
    }

    public static void init(String serverAddress, Messenger messenger) throws IOException {
        ClientIO io = new IOHandler();
        String serverIp = serverAddress.split(":")[0];
        int serverPort = 0;
        try {
            serverPort = Integer.parseInt(serverAddress.split(":")[1]);
        } catch (NumberFormatException e) {
            System.out.println("Wrong port format. Try again.");
            init(messenger);
        }
        System.out.println("Trying to get access to server opened on " + serverAddress);

        try {
            io.connectToServer(serverIp, serverPort);
        } catch (IllegalArgumentException e) {
            throw new IOException("Port out of range!");
        }

        io.sendCommandLine("get_commands_formats");
        CommandValidator validator = new CommandValidator(io.getResponse());


        CommandLineInterface cli = new CommandLineInterface(io, validator, messenger);
        try {
            cli.startListen();
        } catch (IOException e) {
            System.out.println("Some problems with server!\nDo you wanna try to reconnect? (y/any key)");
            String answer = cli.getUserCommand();
            if(answer.toLowerCase().equals("y") || answer.toLowerCase().equals("yes"))
                init(serverAddress, messenger);
            else
                init(messenger);
        }
    }
}
