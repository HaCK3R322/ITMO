package com.androsov.server.commandMagment.commands;

import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Clear extends ListCommand {
    MessengersHandler messenger;
    List<Product> list;

    public Clear(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "clear";
        description = "deletes all products from collection.";
        argumentFormat = "void";
        userAccessible = true;
    }

    public String execute(String[] args) {
        String result;

        list.clear();
        result = messenger.Clear().result;

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.Clear().description;
    }
}
