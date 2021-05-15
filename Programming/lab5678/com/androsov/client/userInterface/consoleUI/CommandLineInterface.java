package com.androsov.client.userInterface.consoleUI;

import com.androsov.client.commandManagment.CommandValidator;
import com.androsov.client.internetConnection.ClientIO;
import com.androsov.client.messenger.EngMessenger;
import com.androsov.client.messenger.Messenger;
import com.androsov.client.messenger.RuMessenger;

import java.io.IOException;
import java.util.Scanner;

public class CommandLineInterface {
    ClientIO io;
    Scanner scanner;
    CommandValidator validator;
    Messenger messenger;


    public CommandLineInterface(ClientIO io, CommandValidator validator, Messenger messenger) {
        this.io = io;
        scanner = new Scanner(System.in);
        this.validator = validator;
        this.messenger = messenger;
    }

    /**
     * основной цикл запросов и ответов на сервер и от сервера
     * @throws IOException
     */
    public void startListen() throws IOException {
        io.sendCommandLine("help");
        System.out.println(io.getResponse());
        System.out.println("-------------------------------------------------");
        System.out.println("Type command here:");

        String command = getUserCommand();
        while(!command.equals("exit")) {
            if(command.split(" ").length > 0) {
                if(!command.split(" ")[0].equals("change_language")) {
                    if(validator.isValid(command)) {
                        io.sendCommandLine(command);
                        System.out.println(io.getResponse());
                    } else {
                        System.out.println(messenger.Wrong_command_or_command_format_try_again());
                    }
                } else {
                    if(command.split(" ").length > 1) {
                        messenger = changeLanguage(command.split(" ")[1]);
                    } else {
                        System.out.println("Please enter new language: ");
                        messenger = changeLanguage(getUserCommand());
                    }
                    io.sendCommandLine(command);
                    System.out.println(io.getResponse());
                }
                command = getUserCommand();
            } else {
                System.out.println(messenger.voidSpace());
            }
        }
    }

    public String getUserCommand() {
        return scanner.nextLine();
    }
    public void print(String str) { System.out.println(str); }

    /**
     * запрашивает адрес сервера у пользователя
     * @return
     */
    public static String askAddress() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter server ip:");
        String serverIp = sc.nextLine();
        if(serverIp.split(":").length != 2) {
            System.out.println("Wrong address format! Address format: <ip>:<port>");
            return askAddress();
        }

        return serverIp;
    }

    /**
     * меняет язык клиента
     * @param language
     * @return
     */
    public Messenger changeLanguage(String language) {
        String lang = language.toLowerCase();
        Messenger newMessenger;

        if(lang.equals("ru"))
            newMessenger = new RuMessenger();
        else
            newMessenger = new EngMessenger();

        return newMessenger;
    }
}