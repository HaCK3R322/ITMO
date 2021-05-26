package com.androsov.server.commandMagment.commands;

import com.androsov.server.commandMagment.Command;
import com.androsov.server.commandMagment.CommandHandler;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;

import java.util.Map;

public class Help extends ListCommand {
    CommandHandler commandHandler;
    MessengersHandler messenger;

    public Help(CommandHandler commandHandler, MessengersHandler messenger) {
        this.commandHandler = commandHandler;
        this.messenger = messenger;

        name = "help";
        description = "If used without an argument, returns a list of commands with a description, otherwise displays help for this command";
        argumentFormat = "String|void";
        userAccessible = true;
    }

    public String execute(String[] args) {

        String result = "";

        if(args.length != 0)
        {
            StringBuilder sb = new StringBuilder();
            for (String arg: args) {
                sb.append(arg);
                sb.append(": ");
                sb.append(commandHandler.getCommand(arg).getDescription());
                sb.append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
            result = sb.toString();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("list of languages: " + messenger.getAllLangsNames() + "\n\n");
            for(Map.Entry<String, Command> commandEntry : commandHandler.commandMap.entrySet()) {
                if(commandEntry.getValue().isUserAccessible()) {
                    sb.append(commandEntry.getKey());
                    sb.append(": ");
                    sb.append(commandEntry.getValue().getDescription());
                    sb.append("\n");
                }
            }
            result = sb.toString();
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.Help().description;
    }
}
