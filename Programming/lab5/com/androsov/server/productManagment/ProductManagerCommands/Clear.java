package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;

public class Clear extends ProductManagerCommand {
    public Clear(ProductManager manager) {
        super(manager);

        name = "clear";
        description = "deletes all products from collection.";
    }

    public String execute(String[] args) {
        String result = "";

        manager.clear();
        result = "List was cleared";

        return result;
    }
}
