package com.androsov.server.CommandMagment;

public abstract interface Command {
    public abstract String execute(String[] args);
    public String getName();
    public String getDescription();
}
