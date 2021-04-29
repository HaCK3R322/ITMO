package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Clear extends ListCommand {
    MessengersHandler messenger;

    public Clear(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "clear";
        description = "deletes all products from collection.";
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
