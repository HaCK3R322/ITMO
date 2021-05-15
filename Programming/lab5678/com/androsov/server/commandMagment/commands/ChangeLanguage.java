package com.androsov.server.commandMagment.commands;

import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;

public class ChangeLanguage extends ListCommand {
    MessengersHandler messenger;

    public ChangeLanguage(MessengersHandler messenger) {
        this.messenger = messenger;

        name = "change_language";
        description = "changes language.";
        argumentFormat = "String";
        userAccessible = true;
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
