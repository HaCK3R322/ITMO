package com.androsov.server.commandMagment;

public interface Command {
    String execute(String[] args);
    String getName();
    String getDescription();
    boolean isUserAccessible();
    String getArgumentFormat();
}
