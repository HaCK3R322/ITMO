package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class CountByPrice extends ListCommand {
    public CountByPrice(List<Product> list) {
        this.list = list;

        name = "count_by_price";
        description = "Shows how many products have that price.";
    }

    public String execute(String[] args) {
        String result = "";

        if(args.length > 0)
            try {
                Integer price = Integer.parseInt(args[0]);
                int count = 0;
                for(int i = 0; i < list.size(); i++) {
                    if(list.get(i).getPrice().equals(price))
                        count++;
                }
                result += count;
            } catch (NumberFormatException e) {
                result = "Wrong number format. Try again.";
            }
        else
            result = "Please, enter argument (Format: <command> <argument>)";

        return result;
    }
}
