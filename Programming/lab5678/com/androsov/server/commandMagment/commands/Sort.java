package com.androsov.server.commandMagment.commands;

import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Sort extends ListCommand {
    MessengersHandler messenger;
    List<Product> list;

    public Sort(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "sort";
        description = "sorts collection by product name.";
        argumentFormat = "void";
        userAccessible = true;
    }

    @Override
    public String execute(String[] args) {
        list.sort(Product::compareTo);
        return messenger.Sort().Sorted;
    }

    @Override
    public String getDescription() {
        return messenger.Sort().description;
    }
}
