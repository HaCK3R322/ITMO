package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.CommandMagment.Command;
import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;

import java.util.Map;

public class Help extends ProductManagerCommand {
    public Help(ProductManager manager) {
        super(manager);

        name = "help";
        description = "If used without an argument, returns a list of commands with a description, otherwise displays help for this command";
    }

    public String execute(String[] args) {

        String result = "";

        for(Map.Entry<String, Command> commandEntry : manager.getCommandHandler().commandMap.entrySet()) {
            result += commandEntry.getKey() + ": " + commandEntry.getValue().getDescription() + "\n";
        }

        if(args.length != 0)
        {
            result = "";

            for (String arg: args) {
                result += arg + ": " + manager.getCommandHandler().getCommand(arg).getDescription() + "\n";
            }

            result = result.substring(0, result.length() - 1);
        }

        return result;
    }
}
