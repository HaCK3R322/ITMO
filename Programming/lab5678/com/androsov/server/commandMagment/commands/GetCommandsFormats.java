package com.androsov.server.commandMagment.commands;

import com.androsov.server.commandMagment.Command;
import com.androsov.server.commandMagment.CommandHandler;
import com.androsov.server.commandMagment.ListCommand;

import java.util.Map;

public class GetCommandsFormats extends ListCommand {
    CommandHandler commandHandler;

    public GetCommandsFormats(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;

        name = "get_commands_formats";
        description = "returns commands and their argument format.";
        argumentFormat = "void";
        userAccessible = false;
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        for(Map.Entry<String, Command> commandEntry : commandHandler.commandMap.entrySet()) {
            if(commandEntry.getValue().isUserAccessible())
                result += commandEntry.getKey() + " " + commandEntry.getValue().getArgumentFormat() + "\n";
        }

        return result;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
