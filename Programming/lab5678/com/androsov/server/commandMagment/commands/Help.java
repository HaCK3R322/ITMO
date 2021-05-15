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
            for (String arg: args) {
                result += arg + ": " + commandHandler.getCommand(arg).getDescription() + "\n";
            }

            result = result.substring(0, result.length() - 1);
        } else {
            result += "list of languages: " + messenger.getAllLangsNames() + "\n\n";
            for(Map.Entry<String, Command> commandEntry : commandHandler.commandMap.entrySet()) {
                if(commandEntry.getValue().isUserAccessible())
                    result += commandEntry.getKey() + ": " + commandEntry.getValue().getDescription() + "\n";
            }
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.Help().description;
    }
}
