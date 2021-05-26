package com.androsov.server.commandMagment.commands;

import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;

import java.util.List;

public class ChangeLanguage extends ListCommand {
    MessengersHandler messenger;

    public ChangeLanguage(MessengersHandler messenger) {
        this.messenger = messenger;

        name = "change_language";
        description = "changes language.";
        argumentFormat = "String";
        userAccessible = true;
    }

    public Response execute(List<Object> args) {
        Response response = new ResponseImpl();

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
