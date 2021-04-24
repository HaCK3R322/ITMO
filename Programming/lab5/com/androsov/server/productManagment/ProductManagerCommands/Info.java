package com.androsov.server.productManagment.ProductManagerCommands;
import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;

public class Info extends ProductManagerCommand {
    public Info(ProductManager manager) {
        super(manager);

        name = "info";
        description = "gives some info about the hole collection.";
    }

    public String execute(String[] args) {
        return manager.getManagerInfo();
    }
}
