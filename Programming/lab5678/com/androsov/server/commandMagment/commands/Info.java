package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.time.LocalDateTime;
import java.util.List;

public class Info extends ListCommand {
    final LocalDateTime initializationTime;
    final MessengersHandler messenger;
    final List<Product> list;

    public Info(List<Product> list, LocalDateTime initializationTime, MessengersHandler messenger) {
        this.list = list;
        this.initializationTime = initializationTime;
        this.messenger = messenger;

        name = "info";
        description = "gives some info about the hole collection.";
        argumentFormat = "void";
        userAccessible = true;
    }

    @Override
    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        final Response response = new ResponseImpl(request.getUser());

        response.setMessage((messenger.Info().Collection_info + ":" + "\n" +
                "   " + messenger.Info().type_Linked_list + " " + "\n" +
                "   " + messenger.Info().initialization_date + ": " + initializationTime.toString() + "\n" +
                "   " + messenger.Info().number_of_elements + ": " + list.size()
        ));

        return response;
    }

    @Override
    public String getDescription() {
        return messenger.Info().description;
    }
}
