package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class RemoveByManufactureCost extends ListCommand {
    public RemoveByManufactureCost(List<Product> list) {
        this.list = list;

        name = "remove_any_by_manufacture_cost";
        description = "-_-";
    }

    @Override
    public String execute(String[] args) {
        String result = "";
        if(args.length > 0) {
            try {
                Float cost = Float.parseFloat(args[0]);
                for(int i = 0; i < list.size(); i++) {
                    if(list.get(i).getManufactureCost().equals(cost)) {
                        list.remove(i);
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                result = "Wrong id format. Please enter long-format argument";
            }
        } else {
            result = "Please, enter cost.";
        }

        return result;
    }
}
