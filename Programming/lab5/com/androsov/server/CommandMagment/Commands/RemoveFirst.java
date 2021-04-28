package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class RemoveFirst extends ListCommand {
    public RemoveFirst(List<Product> list) {
        this.list = list;

        name = "remove_first";
        description = "removes first element of collection.";
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        if(list.size() > 0) {
            result = "Product with id " + list.get(0).getId() + " was removed";
            list.remove(0);
        } else {
            result = "List is already empty.";
        }

        return result;
    }
}
