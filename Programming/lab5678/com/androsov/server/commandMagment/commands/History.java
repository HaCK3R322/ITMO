package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.CommandHandler;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;

import java.util.List;

public class History extends ListCommand {
    final CommandHandler commandHandler;
    final MessengersHandler messenger;

    public History(CommandHandler commandHandler, MessengersHandler messenger) {
        this.messenger = messenger;
        this.commandHandler = commandHandler;

        name = "history";
        description = "Shows 12 last used commands.";
        argumentFormat = "void";
        userAccessible = true;
    }

    @Override
    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        Response response = new ResponseImpl(request.getUser());

        StringBuilder result = new StringBuilder(messenger.History().history + ":\n");

        int NUMBER_OF_LAST_COMMANDS_TO_SHOW = 12;

        int historySize = commandHandler.history.size();
        List<String> lastCommands = commandHandler.history.subList(
                historySize - Math.min(historySize, NUMBER_OF_LAST_COMMANDS_TO_SHOW), historySize
        );

        for (String lastCommand : lastCommands) {
            result.append("   - ").append(lastCommand).append("\n");
        }

        response.setMessage(result.toString());

        return response;
    }

    @Override
    public String getDescription() {
        return messenger.History().description;
    }
}
