package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Sort extends ListCommand {
    public Sort(List<Product> list) {
        this.list = list;

        name = "sort";
        description = "sorts collection by product name.";
    }

    @Override
    public String execute(String[] args) {
        list.sort(Product::compareTo);
        return "Sorted.";
    }
}
