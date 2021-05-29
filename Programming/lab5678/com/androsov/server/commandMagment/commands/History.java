package com.androsov.server.commandMagment.commands;

import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.CommandHandler;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;

import java.util.List;

public class History extends ListCommand {
    CommandHandler commandHandler;
    MessengersHandler messenger;

    public History(CommandHandler commandHandler, MessengersHandler messenger) {
        this.messenger = messenger;
        this.commandHandler = commandHandler;

        name = "history";
        description = "Shows 12 last used commands.";
        argumentFormat = "void";
        userAccessible = true;
    }

    @Override
    public Response execute(List<Object> args) {
        Response response = new ResponseImpl();

        String result = messenger.History().history + ":\n";

        int NUMBER_OF_LAST_COMMANDS_TO_SHOW = 12;

        int historySize = commandHandler.history.size();
        List<String> lastCommands = commandHandler.history.subList(
                historySize - Math.min(historySize, NUMBER_OF_LAST_COMMANDS_TO_SHOW), historySize
        );

        for(int i = 0; i < lastCommands.size(); i++) {
            result += "   - " + lastCommands.get(i) + "\n";
        }

        response.setMessage(result);

        return response;
    }

    @Override
    public String getDescription() {
        return messenger.History().description;
    }
}
