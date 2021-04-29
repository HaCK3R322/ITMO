package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Sort extends ListCommand {
    MessengersHandler messenger;

    public Sort(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "sort";
        description = "sorts collection by product name.";
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
