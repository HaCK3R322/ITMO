package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.time.LocalDateTime;
import java.util.List;

public class Info extends ListCommand {
    LocalDateTime initializationTime;
    MessengersHandler messenger;

    public Info(List<Product> list, LocalDateTime initializationTime, MessengersHandler messenger) {
        this.list = list;
        this.initializationTime = initializationTime;
        this.messenger = messenger;

        name = "info";
        description = "gives some info about the hole collection.";
    }

    public String execute(String[] args) {
        return (messenger.Info().Collection_info + ":" + "\n" +
                "   " + messenger.Info().type_Linked_list + " " + "\n" +
                "   " + messenger.Info().initialization_date + ": " + initializationTime.toString() + "\n" +
                "   " + messenger.Info().number_of_elements + ": " + list.size()
        );
    }

    @Override
    public String getDescription() {
        return messenger.Info().description;
    }
}
