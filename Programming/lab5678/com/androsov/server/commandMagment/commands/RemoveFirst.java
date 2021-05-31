package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class RemoveFirst extends ListCommand {
    final MessengersHandler messenger;
    final List<Product> list;

    public RemoveFirst(List<Product> list, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;

        name = "remove_first";
        description = "removes first element of collection.";
        argumentFormat = "void";
        userAccessible = true;
    }

    @Override
    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        final Response response = new ResponseImpl(request.getUser());

        String result;

        if(list.size() > 0) {
            result = messenger.RemoveFirst().Product_with_id + " " + list.get(0).getId() + " " + messenger.RemoveFirst().was_removed;
            list.remove(0);
        } else {
            result = messenger.RemoveFirst().List_is_already_empty;
        }

        response.setMessage(result);

        return response;
    }

    @Override
    public String getDescription() {
        return messenger.RemoveFirst().description;
    }
}
