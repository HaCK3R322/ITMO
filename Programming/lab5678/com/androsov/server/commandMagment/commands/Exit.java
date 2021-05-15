package com.androsov.server.commandMagment.commands;

import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;

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
    public String execute(String[] args) {
        return "\0";
    }

    @Override
    public String getDescription() {
        return messenger.Exit().description;
    }
}
