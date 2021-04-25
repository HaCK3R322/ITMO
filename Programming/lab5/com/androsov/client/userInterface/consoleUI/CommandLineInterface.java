package com.androsov.client.userInterface.consoleUI;

import com.androsov.client.InternetConnection.ClientIO;

import java.io.IOException;
import java.util.Scanner;

public class CommandLineInterface {
    ClientIO io;

    Scanner scanner;


    public CommandLineInterface(ClientIO io) {
        this.io = io;
        scanner = new Scanner(System.in);
    }

    public void startListen() {
        try {
            io.sendCommandLine("help");
            System.out.println(io.getResponse());
            System.out.println("-------------------------------------------------");
            System.out.println("Type command here:");

            String command = getUserCommand();
            while(!command.equals("exit")) {
                io.sendCommandLine(command);
                System.out.println(io.getResponse());
                command = getUserCommand();
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private String getUserCommand() {
        return scanner.nextLine();
    }

    public static String askIp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter server ip:");
        return sc.nextLine();
    }

    public static String askPort() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter server port:");
        return sc.nextLine();
    }
}