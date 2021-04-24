package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;
import com.androsov.server.productManagment.exceptions.ManagmentException;

public class RemoveByManufactureCost extends ProductManagerCommand {
    public RemoveByManufactureCost(ProductManager manager) {
        super(manager);

        name = "remove_any_by_manufacture_cost";
        description = "lol wf don't you understand?";
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        if(args.length > 0) {
            try {
                Float cost = Float.parseFloat(args[0]);
                manager.removeAnyByManufactureCost(cost);
            } catch (NumberFormatException e) {
                result = "Wrong id format. Please enter long-format argument";
            } catch (ManagmentException e) {
                result = "Removing exception: " + e.getMessage();
            }
        } else {
            result = "Please, enter cost.";
        }

        return result;
    }
}
