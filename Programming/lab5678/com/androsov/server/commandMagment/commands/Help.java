package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.Command;
import com.androsov.server.commandMagment.CommandHandler;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;

import java.util.List;
import java.util.Map;

public class Help extends ListCommand {
    final CommandHandler commandHandler;
    final MessengersHandler messenger;

    public Help(CommandHandler commandHandler, MessengersHandler messenger) {
        this.commandHandler = commandHandler;
        this.messenger = messenger;

        name = "help";
        description = "If used without an argument, returns a list of commands with a description, otherwise displays help for this command";
        argumentFormat = "String|void";
        userAccessible = true;
    }

    @Override
    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        Response response = new ResponseImpl(request.getUser());

        StringBuilder sb = new StringBuilder();
        if(args.size() != 0) {
            for (Object arg: args) {
                sb.append((String) arg);
                sb.append(": ");
                sb.append(commandHandler.getCommand((String) arg).getDescription());
                sb.append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
        } else {
            sb.append("list of languages: ").append(messenger.getAllLangsNames()).append("\n\n");
            for (Map.Entry<String, Command> commandEntry : commandHandler.commandMap.entrySet()) {
                if (commandEntry.getValue().isUserAccessible()) {
                    sb.append(commandEntry.getKey());
                    sb.append(": ");
                    sb.append(commandEntry.getValue().getDescription());
                    sb.append("\n");
                }
            }
        }
        response.setMessage(sb.toString());

        return response;
    }

    @Override
    public String getDescription() {
        return messenger.Help().description;
    }
}
