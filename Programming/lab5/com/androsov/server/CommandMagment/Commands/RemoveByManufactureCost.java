package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class RemoveByManufactureCost extends ListCommand {
    MessengersHandler messenger;

    public RemoveByManufactureCost(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

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
                result = messenger.RemoveByManufactureCost().Wrong_id_format_Please_enter_long_format_argument;
            }
        } else {
            result = messenger.RemoveByManufactureCost().Please_enter_cost;
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.RemoveByManufactureCost().description;
    }
}
