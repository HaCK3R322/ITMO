package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Clear extends ListCommand {
    final MessengersHandler messenger;
    final List<Product> list;

    public Clear(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "clear";
        description = "deletes all products from collection.";
        argumentFormat = "void";
        userAccessible = true;
    }

    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        Response response = new ResponseImpl(request.getUser());

        response.setMessage(execute(new String[]{}));

        return response;
    }

    public String execute(String[] args) {
        String result;

        list.clear();
        result = messenger.Clear().result;

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.Clear().description;
    }
}
