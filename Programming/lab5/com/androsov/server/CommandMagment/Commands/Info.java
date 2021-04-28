package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;

import java.time.LocalDateTime;
import java.util.List;

public class Info extends ListCommand {
    LocalDateTime initializationTime;

    public Info(List<Product> list, LocalDateTime initializationTime) {
        this.list = list;
        this.initializationTime = initializationTime;

        name = "info";
        description = "gives some info about the hole collection.";
    }

    public String execute(String[] args) {
        return ("Collection info:" + "\n" +
                "   type: LinkedList" + "\n" +
                "   initialization date: " + initializationTime.toString() + "\n" +
                "   number of elements: " + list.size()
        );
    }
}
