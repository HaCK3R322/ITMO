package com.androsov.server.CommandMagment;

public interface Command {
    String execute(String[] args);
    String getName();
    String getDescription();
}
