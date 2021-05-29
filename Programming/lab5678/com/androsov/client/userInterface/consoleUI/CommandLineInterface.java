package com.androsov.client.userInterface.consoleUI;

import com.androsov.client.commandManagment.CommandFormatter;
import com.androsov.client.messenger.EngMessenger;
import com.androsov.client.messenger.Messenger;
import com.androsov.client.messenger.RuMessenger;
import com.androsov.client.userInterface.Ui;
import com.androsov.general.IO.IO;
import com.androsov.general.ObjectSerialization;
import com.androsov.general.User;
import com.androsov.general.request.Request;
import com.androsov.general.request.RequestImpl;
import com.androsov.general.response.Response;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface implements Ui {
    User user;

    IO io;
    final private Scanner scanner = new Scanner(System.in);
    final CommandFormatter formatter;
    Messenger messenger;

    public CommandLineInterface(IO io, Messenger messenger, User user) throws IOException {
        this.user = user;
        this.io = io;
        this.messenger = messenger;

        io.send(ObjectSerialization.serialize(new RequestImpl("get_commands_formats", user)));
        formatter = new CommandFormatter(((Response) io.get()).getMessage());
    }

    /**
     * основной цикл запросов и ответов на сервер и от сервера
     * @throws IOException
     */
    @Override
    public void init() throws IOException {
        io.send(ObjectSerialization.serialize(new RequestImpl("help", user)));
        System.out.println(((Response) io.get()).getMessage());
        System.out.println("-------------------------------------------------");
        System.out.println("Type command here:");

        while(true) {
            //get new user command from System.in
            System.out.println(">>> ");
            String command = getCommand();

            if(command.equals("exit")) {
                break;
            }

            //if command not void
            if(formatter.getLength(command) != 0) {
                if(formatter.isValid(command)) {
                    final String name = formatter.getName(command);
                    final List<Object> args = formatter.getArgs(command);
                    final Request request = new RequestImpl(name, args, user);
                    io.send(ObjectSerialization.serialize(request));
                    System.out.println(((Response) io.get()).getMessage());
                } else {
                    System.out.println(messenger.Wrong_command_or_command_format_try_again());
                    break;
                }

                if(formatter.getName(command).equals("change_language")) {
                    if(command.split(" ").length == 2) {
                        messenger = changeLanguage(command.split(" ")[1]);
                    }
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
        System.out.println("Do you wanna reconnect? (type yes|y or type any other key to end program)");
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