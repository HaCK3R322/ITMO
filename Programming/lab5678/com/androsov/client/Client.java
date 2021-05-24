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
        ClientIO io = new AsyncIOHandler();
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
                io.connectToServer(serverIp, serverPort);

                ui = new CommandLineInterface(io, messenger);

                ui.init();

            } catch (IOException e) {
                if(ui != null) {
                    if(!ui.askReconnect())
                        break;
                }
            }
        }
    }
}
