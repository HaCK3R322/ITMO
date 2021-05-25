package com.androsov.client.userInterface.consoleUI;

import com.androsov.client.commandManagment.CommandValidator;
import com.androsov.client.internetConnection.ClientIO;
import com.androsov.client.messenger.EngMessenger;
import com.androsov.client.messenger.Messenger;
import com.androsov.client.messenger.RuMessenger;
import com.androsov.client.userInterface.Ui;

import java.io.IOException;
import java.util.Scanner;

public class CommandLineInterface implements Ui {
    ClientIO io;
    final private Scanner scanner = new Scanner(System.in);
    final CommandValidator validator;
    Messenger messenger;

    public CommandLineInterface(ClientIO io, Messenger messenger) throws IOException {
        this.io = io;
        this.messenger = messenger;

        io.sendCommandLine("get_commands_formats");
        validator = new CommandValidator(io.getResponse());
    }

    /**
     * основной цикл запросов и ответов на сервер и от сервера
     * @throws IOException
     */
    @Override
    public void init() throws IOException {
        io.sendCommandLine("help");
        System.out.println(io.getResponse());
        System.out.println("-------------------------------------------------");
        System.out.println("Type command here:");

        while(true) {
            //get new user command from System.in
            String command = getCommand();

            if(command.equals("exit")) {
                break;
            }

            //if command not void
            if(command.split(" ").length > 0) {
                if(!command.split(" ")[0].equals("change_language")) {

                    //send command - print response
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
                        messenger = changeLanguage(getCommand());
                    }
                    io.sendCommandLine(command);
                    System.out.println(io.getResponse());
                }
            }
        }
    }

    @Override
    public boolean endSession() {
        System.out.println("Do you really wanna exit? (type yes|y  to to end program or type any other key to continue work)");
        String answer = scanner.nextLine();
        if((answer.toLowerCase().equals("y") || answer.toLowerCase().equals("yes"))) {

            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getCommand() { return scanner.nextLine();  }

    @Override
    public void sendResponse(String str) { System.out.println(str); }

    @Override
    public boolean askReconnect() {
        System.out.println("Server connection problems!\n Do you wanna reconnect? (type yes|y or type any other key to end program)");
        String answer = scanner.nextLine();
        return (answer.toLowerCase().equals("y") || answer.toLowerCase().equals("yes"));
    }

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