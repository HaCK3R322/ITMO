package com.androsov.server.commandMagment;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.server.productManagment.exceptions.CommandBuildError;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CommandHandler {
    public HashMap<String, Command> commandMap;
    public List<String> history;

    public CommandHandler() {
        commandMap = new HashMap<>();
        history = new LinkedList<>();
    }

    public void registryCommand(Command command) throws CommandBuildError {
        CommandStructureChecker.check(command);
        commandMap.put(command.getName(), command);
    }

    public Response executeCommand(Request request) {
        history.add(request.getCommandName());
        return commandMap.get(request.getCommandName()).execute(request.getArgs());
    }

    public String executeCommand(String commandLine) {
        String result = "";

        String name = CommandFormatter.getName(commandLine);
        String[] args = CommandFormatter.getArgs(commandLine);

        try {
            result = executeCommand(name, args);
        } catch (NullPointerException e) {
            result = "No such command. Type \"help\" to see available commands";
        }

        return result;
    }

    public String executeCommand(String name, String[] args) {
        history.add(name);
        return commandMap.get(name).execute(args);
    }

    public Command getCommand(String name) {
        return commandMap.get(name);
    }
}
