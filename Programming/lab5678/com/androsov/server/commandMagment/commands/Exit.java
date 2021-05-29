package com.androsov.server.commandMagment.commands;

import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;

import java.util.List;

public class Exit extends ListCommand {
    MessengersHandler messenger;

    public Exit(MessengersHandler messenger) {
        this.messenger = messenger;

        name = "exit";
        description = "ends session.";
        argumentFormat = "void";
        userAccessible = true;
    }

    @Override
    public Response execute(List<Object> args) {
        Response response = new ResponseImpl();
        response.setMessage("\0");
        return response;
    }

    @Override
    public String getDescription() {
        return messenger.Exit().description;
    }
}
