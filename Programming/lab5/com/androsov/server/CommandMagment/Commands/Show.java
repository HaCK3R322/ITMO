package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Show extends ListCommand {
    MessengersHandler messenger;

    public Show(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "show";
        description = "gives info about each product.";
    }

    public String execute(String[] args) {
        String result = "";

        if(list.size() > 0) {
            for(int i = 0; i < list.size(); i++) {
                result += list.get(i).toString();
                if(i != list.size() - 1)
                    result += "\n";
            }
        } else {
            result = "|list is empty|";
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.Show().description;
    }
}
