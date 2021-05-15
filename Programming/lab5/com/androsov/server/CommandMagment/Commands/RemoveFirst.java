package com.androsov.server.commandMagment.commands;

import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class RemoveFirst extends ListCommand {
    MessengersHandler messenger;
    List<Product> list;

    public RemoveFirst(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "remove_first";
        description = "removes first element of collection.";
        argumentFormat = "void";
        userAccessible = true;
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        if(list.size() > 0) {
            result = messenger.RemoveFirst().Product_with_id + " " + list.get(0).getId() + " " + messenger.RemoveFirst().was_removed;
            list.remove(0);
        } else {
            result = messenger.RemoveFirst().List_is_already_empty;
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.RemoveFirst().description;
    }
}
