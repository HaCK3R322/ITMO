package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;

import java.awt.*;

public class Exit extends ListCommand {
    MessengersHandler messenger;

    public Exit(MessengersHandler messenger) {
        this.messenger = messenger;

        name = "exit";
        description = "ends session.";
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
