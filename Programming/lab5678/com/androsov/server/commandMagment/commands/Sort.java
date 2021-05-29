package com.androsov.server.commandMagment.commands;

import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Sort extends ListCommand {
    MessengersHandler messenger;
    List<Product> list;

    public Sort(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "sort";
        description = "sorts collection by product name.";
        argumentFormat = "void";
        userAccessible = true;
    }

    @Override
    public Response execute(List<Object> args) {
        list.sort(Product::compareTo);
        final Response response = new ResponseImpl();
        response.setMessage(messenger.Sort().Sorted);
        return response;
    }

    @Override
    public String getDescription() {
        return messenger.Sort().description;
    }
}
