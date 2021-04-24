package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;

public class Show extends ProductManagerCommand {
    public Show(ProductManager manager) {
        super(manager);

        name = "show";
        description = "gives info about each product.";
    }

    public String execute(String[] args) {
        return manager.getProductsInfo();
    }
}
