package com.androsov.client;

import com.androsov.client.commandManagment.CommandValidator;
import com.androsov.client.internetConnection.AsyncIOHandler;
import com.androsov.client.internetConnection.ClientIO;
import com.androsov.client.messenger.EngMessenger;
import com.androsov.client.messenger.Messenger;
import com.androsov.client.userInterface.Ui;
import com.androsov.client.userInterface.consoleUI.CommandLineInterface;

import java.io.IOException;
import java.util.Scanner;

//вариант 296173

public class Client {
    public static void main(String[] args) {
        Messenger messenger = new EngMessenger();

        AsyncIOHandler asyncIO = new AsyncIOHandler();
        ClientIO io = asyncIO;
        Ui ui = null;

        while (true) {
            String serverAddress = CommandLineInterface.askAddress();
            try {
                String serverIp = serverAddress.split(":")[0];
                int serverPort;
                try {
                    serverPort = Integer.parseInt(serverAddress.split(":")[1]);
                } catch (NumberFormatException e) {
                    return;
                }
                try {
                    io.connectToServer(serverIp, serverPort);
                } catch (IllegalArgumentException e) {
                    System.out.println("Incorrect address: " + e.getMessage() + "\nTry again.");
                    continue;
                }

                ui = new CommandLineInterface(io, messenger);

                ui.init();
                //asyncIO.close();
//                if(ui.endSession()) {
//                    break;
//                }
            } catch (IOException e) {
                System.out.println("Server connection error: " + e.getMessage());
                if(ui != null) {
                    if(!ui.askReconnect())
                        break;
                }
            }
        }
    }
}
