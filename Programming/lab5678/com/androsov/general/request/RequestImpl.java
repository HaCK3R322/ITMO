package com.androsov.general.request;

import java.util.List;

public class RequestImpl implements Request {
    private final String commandName;
    private final List<Object> argsList;

    public RequestImpl(String commandName, List<Object> argsList) {
        this.commandName = commandName;
        this.argsList = argsList;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public List<Object> getArgs() {
        return argsList;
    }
}
