package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;
import com.androsov.server.productManagment.exceptions.ManagementException;

public class RemoveById extends ProductManagerCommand {
    public RemoveById(ProductManager manager) {
        super(manager);

        name = "remove";
        description = "removes product with given id.";
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        if(args.length > 0) {
            try {
                long id = Long.parseLong(args[0]);
                manager.removeById(id);
            } catch (NumberFormatException e) {
                result = "Wrong id format. Please enter long-format argument";
            } catch (ManagementException e) {
                result = "Removing exception: " + e.getMessage();
            }
        } else {
            result = "Please, enter id.";
        }

        return result;
    }
}
