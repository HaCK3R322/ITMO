package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;

public class Sort extends ProductManagerCommand {
    public Sort(ProductManager manager) {
        super(manager);

        name = "sort";
        description = "sorts collection by product name.";
    }

    @Override
    public String execute(String[] args) {
        manager.sort();

        return "Sorted.";
    }
}
