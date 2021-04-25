package com.androsov;

import com.androsov.server.InternetConnection.ServerIO;
import com.androsov.server.InternetConnection.SystemIOHandler;
import com.androsov.server.productManagment.ListProductManager;
import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.exceptions.ContentException;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServerIO sio = new SystemIOHandler();

        ProductManager manager = null;
        try {
            manager = new ListProductManager("LAB5_CONTENT", sio);
        } catch (IOException | ContentException e) {
            System.out.println("Program start error: " + e.getMessage());
        }

        String line;
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------------------------------------------");
        System.out.println(manager.getCommandHandler().executeCommand("help"));
        System.out.println("-------------------------------------------------");
        System.out.println("Type command here:");
        boolean stop = false;
        while (!stop) {
            line = sc.nextLine();
            if(!line.equals("exit")) {
                System.out.println(manager.getCommandHandler().executeCommand(line));
            } else {
                stop = true;
            }
        }
    }
}
