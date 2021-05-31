package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;

import java.util.List;

public class ChangeLanguage extends ListCommand {
    final MessengersHandler messenger;

    public ChangeLanguage(MessengersHandler messenger) {
        this.messenger = messenger;

        name = "change_language";
        description = "changes language.";
        argumentFormat = "String";
        userAccessible = true;
    }

    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        Response response = new ResponseImpl(request.getUser());

        if(args.size() > 0)
            response.setMessage(messenger.changeLanguage((String) args.get(0)));
        else
            response.setMessage("Please, enter language");

        return response;
    }

    public String execute(String[] args) {
        String result = "";

        if(args.length > 0)
            result = messenger.changeLanguage(args[0]);
        else
            result = "Please, enter language";

        return result;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
