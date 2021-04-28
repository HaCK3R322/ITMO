package com.androsov.server.CommandMagment.Commands;

import com.androsov.server.CommandMagment.CommandHandler;
import com.androsov.server.CommandMagment.ListCommand;
import com.androsov.server.lab5Plains.Product;
import com.androsov.server.productManagment.exceptions.SelfCycledScriptChainException;
import com.androsov.server.scripting.Script;

import java.io.IOException;
import java.util.List;

public class ExecuteScript extends ListCommand {
    CommandHandler commandHandler;

    public ExecuteScript(List<Product> list, CommandHandler commandHandler) {
        this.list = list;
        this.commandHandler = commandHandler;

        name = "execute_script";
        description = "executes script. Command format: execute_script <path to script>.";
    }

    public String execute(String[] args) {
        String result = "";

        try {
            String scriptName = args[0];
            Script script = new Script(scriptName);

            for(int i = 0; i < script.commands.size(); i++) {
                result += "script: " + commandHandler.executeCommand(script.takeCommand()) + '\n';
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
