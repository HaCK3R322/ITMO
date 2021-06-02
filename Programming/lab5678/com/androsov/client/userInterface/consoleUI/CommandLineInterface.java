package com.androsov.client.userInterface.consoleUI;

import com.androsov.client.commandManagment.CommandValidator;
import com.androsov.client.messenger.EngMessenger;
import com.androsov.client.messenger.Messenger;
import com.androsov.client.messenger.RuMessenger;
import com.androsov.client.userInterface.Ui;
import com.androsov.general.CommandFormatter;
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
    final private User user;

    final private IO io;
    final private Scanner scanner = new Scanner(System.in);
    final private CommandValidator validator;
    private Messenger messenger;

    public CommandLineInterface(IO io, Messenger messenger, User user) throws IOException {
        this.user = user;
        this.io = io;
        this.messenger = messenger;

        io.send(ObjectSerialization.serialize(new RequestImpl("get_commands_formats", user)));
        validator = new CommandValidator(((Response) ObjectSerialization.deserialize(io.get())).getMessage());
    }

    /**
     * основной цикл запросов и ответов на сервер и от сервера
     * @throws IOException
     */
    @Override
    public void init() throws IOException {
        io.send(ObjectSerialization.serialize(new RequestImpl("help", user)));
        System.out.println(((Response) ObjectSerialization.deserialize(io.get())).getMessage());
        System.out.println("-------------------------------------------------");
        System.out.println("Type command here:");

        while(true) {
            //get new user command from System.in
            System.out.print(">>> ");
            String command = getCommand();

            if(command.equals("exit")) {
                break;
            }

            //if command not void
            if(CommandFormatter.getLength(command) != 0) {
                if(validator.isValid(command)) {
                    final String name = CommandFormatter.extractName(command);
                    final List<Object> args = CommandFormatter.extractArgs(command);
                    final Request request = new RequestImpl(name, args, user);
                    io.send(ObjectSerialization.serialize(request));
                    System.out.println(((Response) ObjectSerialization.deserialize(io.get())).getMessage());
                } else {
                    System.out.println(messenger.Wrong_command_or_command_format_try_again());
                    continue;
                }

                if(CommandFormatter.extractName(command).equals("change_language")) {
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
        return answer.toLowerCase().equals("y") || answer.toLowerCase().equals("yes");
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