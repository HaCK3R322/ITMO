package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.CommandHandler;
import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.Messengers.MessengersHandler;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class History extends ListCommand {
    CommandHandler commandHandler;
    MessengersHandler messenger;

    public History(List<Product> list, CommandHandler commandHandler, MessengersHandler messenger) {
        this.list = list;
        this.messenger = messenger;
        this.commandHandler = commandHandler;

        name = "history";
        description = "Shows 12 last used commands.";
    }

    @Override
    public String execute(String[] args) {
        String result = messenger.History().history + ":\n";

        int NUMBER_OF_LAST_COMMANDS_TO_SHOW = 12;

        int historySize = commandHandler.history.size();
        List<String> lastCommands = commandHandler.history.subList(
                historySize - Math.min(historySize, NUMBER_OF_LAST_COMMANDS_TO_SHOW), historySize
        );

        for(int i = 0; i < lastCommands.size(); i++) {
            result += "   - " + lastCommands.get(i) + "\n";
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.History().description;
    }
}
