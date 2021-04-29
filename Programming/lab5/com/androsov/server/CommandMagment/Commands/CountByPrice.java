package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class CountByPrice extends ListCommand {
    MessengersHandler messenger;

    public CountByPrice(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

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
                result = messenger.CountByPrice().Wrong_number_format;
            }
        else
            result = messenger.CountByPrice().Please_enter_argument;

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.CountByPrice().description;
    }
}
