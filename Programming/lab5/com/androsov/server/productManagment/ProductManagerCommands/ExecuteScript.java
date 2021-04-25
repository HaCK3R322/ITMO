package com.androsov.server.productManagment.ProductManagerCommands;

import com.androsov.server.productManagment.ProductManager;
import com.androsov.server.productManagment.ProductManagerCommands.Command.ProductManagerCommand;
import com.androsov.server.productManagment.exceptions.SelfCycledScriptChainException;
import com.androsov.server.scripting.Script;

import java.io.IOException;

public class ExecuteScript extends ProductManagerCommand {
    public ExecuteScript(ProductManager manager) {
        super(manager);

        name = "execute_script";
        description = "executes script. Command format: execute_script <path to script>.";
    }

    public String execute(String[] args) {
        String result = "";

        try {
            String scriptName = args[0];
            Script script = new Script(scriptName);

            result += "script name: " + scriptName + '\n';
            result += "lines number: " + script.commands.size() + '\n';
            result += "<start script execution>\n";

            for(int i = 0; i < script.commands.size(); i++) {
                result += manager.getCommandHandler().executeCommand(script.takeCommand()) + '\n';
            }

            result += "<script " + scriptName + " executed.>";

        } catch (NullPointerException e) {
            result = "Please, enter script name!";
        } catch (IOException | SelfCycledScriptChainException e) {
            result = "<Script error>: " + e.getMessage();
        }

        return result;
    }
}
