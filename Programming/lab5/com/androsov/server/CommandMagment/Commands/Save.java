package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.ListSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Save extends ListCommand {
    File file;
    MessengersHandler messenger;

    public Save(List<Product> list, File file, MessengersHandler messenger) {
        this.list = list;
        this.file = file;
        this.messenger = messenger;

        name = "save";
        description = "Saves collection.";
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        if(args.length == 0) {
            try {
                ListSerializer.serializeListToFile(file, list);
                result = messenger.Save().saved;
            } catch (IOException e) {
                result = "IOException while saving: " + e.getMessage();
            }
        } else {
            try {
                File fileToSave = new File(args[0]);
                ListSerializer.serializeListToFile(fileToSave, list);
                result = messenger.Save().saved;
            } catch (IOException e) {
                result = "IOException while saving: " + e.getMessage();
            }
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.Save().description;
    }
}
