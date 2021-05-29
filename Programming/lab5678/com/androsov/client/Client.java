package com.androsov.client;

import com.androsov.client.internetConnection.AsyncIOHandler;
import com.androsov.client.internetConnection.ClientIOold;
import com.androsov.client.messenger.EngMessenger;
import com.androsov.client.messenger.Messenger;
import com.androsov.client.userInterface.Ui;
import com.androsov.client.userInterface.consoleUI.CommandLineInterface;
import com.androsov.general.IO.IO;

import java.io.*;

//вариант 296173

class Person implements Serializable {
    public Integer number;
    public Person(int n) {
        number = n;
    }
}

public class Client {
    public static void main(String[] args) {

        try {
            Person p = new Person(55555555);
            ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(p);

            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            Person p2 = (Person)objectInputStream.readObject();
            System.out.println(p2.number);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }






        Messenger messenger = new EngMessenger();

        AsyncIOHandler asyncIO = new AsyncIOHandler();
        IO io = asyncIO;
        Ui ui;

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
                if(ui.askReconnect() != true) {
                    break;
                }
            } catch (IOException e) {
                System.out.println("Server connection error: " + e.getMessage());
            }
        }
    }
}
