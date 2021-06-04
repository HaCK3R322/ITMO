package com.androsov.server.commandMagment;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.server.commandMagment.commands.*;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.productManagment.ProductBuilder;
import com.androsov.server.productManagment.exceptions.CommandBuildError;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CommandHandler {
    public final HashMap<String, Command> commandMap;
    public final List<String> history;

    public CommandHandler() {
        commandMap = new HashMap<>();
        history = new LinkedList<>();
    }

    public void registryCommand(Command command) throws CommandBuildError {
        CommandStructureChecker.check(command);
        commandMap.put(command.getName(), command);
    }

    public void init(List list, MessengersHandler messengersHandler, ProductBuilder productBuilder, File lab5ContentFile, LocalDateTime initializationTime) {
        registryCommand(new ChangeLanguage(messengersHandler));
        registryCommand(new Add(list, productBuilder, messengersHandler));
        registryCommand(new AverageOfManufactureCost(list, messengersHandler));
        registryCommand(new Clear(list, messengersHandler));
        registryCommand(new CountByPrice(list, messengersHandler));
        registryCommand(new ExecuteScript(this, messengersHandler));
        registryCommand(new Help(this, messengersHandler));
        registryCommand(new History(this, messengersHandler));
        registryCommand(new Info(list, initializationTime, messengersHandler));
        registryCommand(new RemoveById(list, messengersHandler));
        registryCommand(new RemoveByManufactureCost(list, messengersHandler));
        registryCommand(new RemoveFirst(list, messengersHandler));
        registryCommand(new Save(list, lab5ContentFile, messengersHandler));
        registryCommand(new Show(list, messengersHandler));
        registryCommand(new Sort(list, messengersHandler));
        registryCommand(new UpdateById(list, productBuilder, messengersHandler));
        registryCommand(new Exit(messengersHandler));
        registryCommand(new GetCommandsFormats(this));
    }

    public Response executeCommand(Request request) {
        try {
            history.add(request.getCommandName());
        } catch (NullPointerException ignored) {}
        return commandMap.get(request.getCommandName()).execute(request);
    }

    public Command getCommand(String name) {
        return commandMap.get(name);
    }
}
