package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class RemoveFirst extends ListCommand {
    MessengersHandler messenger;

    public RemoveFirst(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "remove_first";
        description = "removes first element of collection.";
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
