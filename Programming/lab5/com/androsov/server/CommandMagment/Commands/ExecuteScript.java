package com.androsov.server.commandMagment.commands;

import com.androsov.server.commandMagment.CommandHandler;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.productManagment.exceptions.SelfCycledScriptChainException;
import com.androsov.server.scripting.Script;

import java.io.IOException;

public class ExecuteScript extends ListCommand {
    CommandHandler commandHandler;
    MessengersHandler messenger;

    public ExecuteScript(CommandHandler commandHandler, MessengersHandler messenger) {
        this.commandHandler = commandHandler;
        this.messenger = messenger;

        name = "execute_script";
        description = "executes script. Command format: execute_script <path to script>.";
        argumentFormat = "String";
        userAccessible = true;
    }

    public String execute(String[] args) {
        String result = "";

        try {
            String scriptName = args[0];
            Script script = new Script(scriptName);

            for(int i = 0; i < script.commands.size(); i++) {
                result += messenger.ExecuteScript().script + ": " + commandHandler.executeCommand(script.takeCommand()) + '\n';
            }

            result += "<" + messenger.ExecuteScript().script + scriptName + messenger.ExecuteScript().executed + ".>";

        } catch (NullPointerException e) {
            result = messenger.ExecuteScript().Please_enter_script_name;
        } catch (IOException | SelfCycledScriptChainException e) {
            result = "<" + messenger.ExecuteScript().Script_error + ">: " + e.getMessage();
        }

        return result;
    }

    @Override
    public String getDescription() {
        return messenger.ExecuteScript().description;
    }
}
