package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.CommandHandler;
import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;

import java.util.List;

public class History extends ListCommand {
    CommandHandler commandHandler;

    public History(List<Product> list, CommandHandler commandHandler) {
        this.list = list;

        name = "history";
        description = "Shows 12 last used commands.";
    }

    @Override
    public String execute(String[] args) {
        String result = "history:\n";

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
}
