package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.Messenger;
import com.androsov.server.Messengers.MessengersHandler;

public class ChangeLanguage extends ListCommand {
    MessengersHandler messenger;

    public ChangeLanguage(MessengersHandler messenger) {
        this.messenger = messenger;

        name = "change_language";
        description = "changes language.";
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
