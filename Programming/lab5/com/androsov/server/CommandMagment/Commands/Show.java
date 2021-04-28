package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class Show extends ListCommand {
    public Show(List<Product> list) {
        this.list = list;

        name = "show";
        description = "gives info about each product.";
    }

    public String execute(String[] args) {
        String result = "";

        for(int i = 0; i < list.size(); i++) {
            result += list.get(i).toString();
            if(i != list.size() - 1)
                result += "\n";
        }

        return result;
    }
}
