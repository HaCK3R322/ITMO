package com.androsov.server.productManagment.ProductManagerCommands.Command;

import com.androsov.server.CommandMagment.Command;
import com.androsov.server.productManagment.ListProductManager;
import com.androsov.server.productManagment.ProductManager;

public abstract class ProductManagerCommand implements Command {
    public ProductManagerCommand(ProductManager manager) {
        this.manager = manager;
    }

    public String name;
    public String description;

    public ProductManager manager;

    public String getName() {
        return name;
    }
    public String getDescription() {return description; }
}
