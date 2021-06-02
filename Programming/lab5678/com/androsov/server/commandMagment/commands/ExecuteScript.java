package com.androsov.server.commandMagment.commands;

import com.androsov.general.CommandFormatter;
import com.androsov.general.request.Request;
import com.androsov.general.request.RequestImpl;
import com.androsov.general.response.Response;
import com.androsov.general.response.ResponseImpl;
import com.androsov.server.commandMagment.CommandHandler;
import com.androsov.server.commandMagment.ListCommand;
import com.androsov.server.messengers.MessengersHandler;
import com.androsov.server.productManagment.exceptions.SelfCycledScriptChainException;
import com.androsov.server.scripting.Script;

import java.io.IOException;
import java.util.List;

public class ExecuteScript extends ListCommand {
    final CommandHandler commandHandler;
    final MessengersHandler messenger;

    public ExecuteScript(CommandHandler commandHandler, MessengersHandler messenger) {
        this.commandHandler = commandHandler;
        this.messenger = messenger;

        name = "execute_script";
        description = "executes script. Command format: execute_script <path to script>.";
        argumentFormat = "String";
        userAccessible = true;
    }

    public Response execute(Request request) {
        List<Object> args = request.getArgs();
        Response response = new ResponseImpl(request.getUser());
        String result = "";

        try {
            String scriptName = (String) args.get(0);
            Script script = new Script(scriptName);

            for (String commandLine : script.commands) {
                final Request requestFromScript = new RequestImpl(CommandFormatter.extractName(commandLine), CommandFormatter.extractArgs(commandLine), request.getUser());
                result += messenger.ExecuteScript().script + ": " + commandHandler.executeCommand(requestFromScript) + '\n';
            }

            result += "<" + messenger.ExecuteScript().script + scriptName + messenger.ExecuteScript().executed + ".>";

        } catch (NullPointerException e) {
            result = messenger.ExecuteScript().Please_enter_script_name;
        } catch (IOException | SelfCycledScriptChainException e) {
            result = "<" + messenger.ExecuteScript().Script_error + ">: " + e.getMessage();
        }

        response.setMessage(result);
        return response;
    }

    @Override
    public String getDescription() {
        return messenger.ExecuteScript().description;
    }
}
