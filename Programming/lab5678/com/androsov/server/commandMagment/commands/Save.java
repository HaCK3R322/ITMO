package com.androsov.server.commandMagment.commands;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.ListSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Save extends ListCommand {
    final File file;
    final MessengersHandler messenger;
    final List<Product> list;

    public Save(List<Product> list, File file, MessengersHandler messenger) {
        this.list = list;
        this.file = file;
        this.messenger = messenger;

        name = "save";
        description = "Saves collection.";
        argumentFormat = "void";
        userAccessible = false;
    }

    @Override
    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        final Response response = new ResponseImpl(request.getUser());
        String result;

        if(args.size() == 0) {
            try {
                ListSerializer.serializeListToFile(file, list);
                result = messenger.Save().saved;
            } catch (IOException e) {
                result = "IOException while saving: " + e.getMessage();
            }
        } else {
            try {
                File fileToSave = new File((String) args.get(0));
                ListSerializer.serializeListToFile(fileToSave, list);
                result = messenger.Save().saved;
            } catch (IOException e) {
                result = "IOException while saving: " + e.getMessage();
            }
        }

        response.setMessage(result);
        return response;
    }

    @Override
    public String getDescription() {
        return messenger.Save().description;
    }
}
