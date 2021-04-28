package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Clear extends ListCommand {
    public Clear(List<Product> list) {
        this.list = list;

        name = "clear";
        description = "deletes all products from collection.";
    }

    public String execute(String[] args) {
        String result;

        list.clear();
        result = "List was cleared";

        return result;
    }
}
