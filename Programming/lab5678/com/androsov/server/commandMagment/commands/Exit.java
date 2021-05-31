package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;

import java.util.List;

public class Exit extends ListCommand {
    final MessengersHandler messenger;

    public Exit(MessengersHandler messenger) {
        this.messenger = messenger;

        name = "exit";
        description = "ends session.";
        argumentFormat = "void";
        userAccessible = true;
    }

    @Override
    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        Response response = new ResponseImpl(request.getUser());
        response.setMessage("\0");
        return response;
    }

    @Override
    public String getDescription() {
        return messenger.Exit().description;
    }
}
