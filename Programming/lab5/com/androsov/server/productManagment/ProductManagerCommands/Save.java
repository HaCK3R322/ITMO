package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;

import java.io.IOException;

public class Save extends ProductManagerCommand {
    public Save(ProductManager manager) {
        super(manager);

        name = "save";
        description = "Saves collection.";
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        try {
            manager.serialize();
        } catch (IOException e) {
            result = "IOException while saving: " + e.getMessage();
        }

        return result;
    }
}
