package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;
import com.androsov.server.productManagment.exceptions.ManagmentException;

public class RemoveFirst extends ProductManagerCommand {
    public RemoveFirst(ProductManager manager) {
        super(manager);

        name = "remove_first";
        description = "removes first element of collection.";
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        try {
            manager.removeFirst();
        } catch (ManagmentException e) {
            result = "Removing exception: " + e.getMessage();
        }

        return result;
    }
}
