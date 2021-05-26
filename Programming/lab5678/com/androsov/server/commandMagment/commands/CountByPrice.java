package com.androsov.server.commandMagment.commands;

import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class CountByPrice extends ListCommand {
    MessengersHandler messenger;
    List<Product> list;

    public CountByPrice(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "count_by_price";
        description = "Shows how many products have that price.";
        argumentFormat = "Integer";
        userAccessible = true;
    }

    public Response execute(List<Object> args) {
        Response response = new ResponseImpl();

        StringBuilder result = new StringBuilder();
        if(args.size() > 0)
            try {
                Integer price = (Integer) args.get(0);
                int count = 0;

                count += list.stream()
                        .map(Product::getPrice)
                        .filter(i -> i.equals(price))
                        .mapToInt(i -> i)
                        .count();

                result.append(count);
            } catch (NumberFormatException e) {
                result.append(messenger.CountByPrice().Wrong_number_format);
            }
        else
            result.append(messenger.CountByPrice().Please_enter_argument);

        response.setMessage(result.toString());
        return response;
    }

    public String execute(String[] args) {
        String result = "";

        if(args.length > 0)
            try {
                Integer price = Integer.parseInt(args[0]);
                int count = 0;

                count += list.stream()
                        .map(Product::getPrice)
                        .filter(i -> i.equals(price))
                        .mapToInt(i -> i)
                        .count();

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
