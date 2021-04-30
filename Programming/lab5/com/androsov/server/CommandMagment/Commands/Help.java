package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.Command;
import com.androsov.server.CommandMagment.CommandHandler;
import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;
import java.util.Map;

public class Help extends ListCommand {
    CommandHandler commandHandler;
    MessengersHandler messenger;

    public Help(List<Product> list, CommandHandler commandHandler, MessengersHandler messenger) {
        this.list = list;
        this.commandHandler = commandHandler;
        this.messenger = messenger;

        name = "help";
        description = "If used without an argument, returns a list of commands with a description, otherwise displays help for this command";
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
                try {
                    if(!commandEntry.getValue().getDescription().equals(null))
                        result += commandEntry.getKey() + ": " + commandEntry.getValue().getDescription() + "\n";
                    else
                        result += messenger.Help().doesnt_have_description;
                } catch (NullPointerException e) {
                    result += commandEntry.getKey() + ": " + messenger.Help().doesnt_have_description;
                }
            }
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.Help().description;
    }
}
