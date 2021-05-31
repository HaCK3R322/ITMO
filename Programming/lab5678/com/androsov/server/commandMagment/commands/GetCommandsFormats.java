package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.Command;
import com.androsov.server.commandMagment.CommandHandler;
import com.androsov.server.commandMagment.ListCommand;

import java.util.List;
import java.util.Map;

public class GetCommandsFormats extends ListCommand {
    final CommandHandler commandHandler;

    public GetCommandsFormats(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;

        name = "get_commands_formats";
        description = "returns commands and their argument format.";
        argumentFormat = "void";
        userAccessible = false;
    }

    @Override
    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        Response response = new ResponseImpl(request.getUser());
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Command> commandEntry : commandHandler.commandMap.entrySet()) {
            if(commandEntry.getValue().isUserAccessible()) {
                sb.append(commandEntry.getKey());
                sb.append(" ");
                sb.append(commandEntry.getValue().getArgumentFormat());
                sb.append("\n");
            }
        }
        //deleting last "\n", just for beauty
        sb.deleteCharAt(sb.length() - 1);
        response.setMessage(sb.toString());
        return response;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
