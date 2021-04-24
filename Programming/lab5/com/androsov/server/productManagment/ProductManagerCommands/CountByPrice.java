package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;
import com.androsov.server.productManagment.exceptions.WrongArgumentTypeException;

public class CountByPrice extends ProductManagerCommand {
    public CountByPrice(ProductManager manager) {
        super(manager);

        name = "count_by_price";
        description = "shows how many products have that price.";
    }

    public String execute(String[] args) {
        String result = "";

        if(args.length > 0)
            try {
                result += manager.countByPrice(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                result = "Wrong number format. Try again.";
            }
        else
            result = "Please, enter argument (Format: <command> <argument>)";

        return result;
    }
}
