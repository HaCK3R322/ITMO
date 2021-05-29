package com.androsov.server.commandMagment.commands;

import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Show extends ListCommand {
    MessengersHandler messenger;
    List<Product> list;

    public Show(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "show";
        description = "gives info about each product.";
        argumentFormat = "void";
        userAccessible = true;
    }

    @Override
    public Response execute(List<Object> args) {
        final Response response = new ResponseImpl();
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

        response.setMessage(result);
        return response;
    }

    @Override
    public String getDescription() {
        return messenger.Show().description;
    }
}
