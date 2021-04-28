package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.Command;
import com.androsov.server.CommandMagment.CommandHandler;
import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;

import java.util.List;
import java.util.Map;

public class Help extends ListCommand {
    CommandHandler commandHandler;

    public Help(List<Product> list, CommandHandler commandHandler) {
        this.list = list;
        this.commandHandler = commandHandler;

        name = "help";
        description = "If used without an argument, returns a list of commands with a description, otherwise displays help for this command";
    }

    public String execute(String[] args) {

        String result = "";

        for(Map.Entry<String, Command> commandEntry : commandHandler.commandMap.entrySet()) {
            result += commandEntry.getKey() + ": " + commandEntry.getValue().getDescription() + "\n";
        }

        if(args.length != 0)
        {
            result = "";

            for (String arg: args) {
                result += arg + ": " + commandHandler.getCommand(arg).getDescription() + "\n";
            }

            result = result.substring(0, result.length() - 1);
        }

        return result;
    }
}
