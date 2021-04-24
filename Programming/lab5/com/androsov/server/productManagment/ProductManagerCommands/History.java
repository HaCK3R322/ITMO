package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;

import java.util.List;

public class History extends ProductManagerCommand {
    public History(ProductManager manager) {
        super(manager);

        name = "history";
        description = "Shows 12 last used commands.";
    }

    @Override
    public String execute(String[] args) {
        String result = "history:\n";

        int NUMBER_OF_LAST_COMMANDS_TO_SHOW = 12;

        int historySize = manager.getCommandHandler().history.size();
        List<String> lastCommands = manager.getCommandHandler().history.subList(
                historySize - Math.min(historySize, NUMBER_OF_LAST_COMMANDS_TO_SHOW), historySize
        );

        for(int i = 0; i < lastCommands.size(); i++) {
            result += "   - " + lastCommands.get(i) + "\n";
        }

        return result;
    }
}
