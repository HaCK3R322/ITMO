package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;

public class AverageOfManufactureCost extends ProductManagerCommand {
    public AverageOfManufactureCost(ProductManager manager) {
        super(manager);

        name = "average_of_manufacture_cost";
        description = "show average of manufacture cost.";
    }

    public String execute(String[] args) {
        String result = "";

        result += manager.averageOfManufactureCost();

        return result;
    }
}
