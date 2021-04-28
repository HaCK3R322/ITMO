package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.ListSerializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Save extends ListCommand {
    File file;

    public Save(List<Product> list, File file) {
        this.list = list;
        this.file = file;

        name = "save";
        description = "Saves collection.";
    }

    @Override
    public String execute(String[] args) {
        String result = "";

        try {
            ListSerializer.serializeListToFile(file, list);
            result = "List was serialized.";
        } catch (IOException e) {
            result = "IOException while saving: " + e.getMessage();
        }

        return result;
    }
}
