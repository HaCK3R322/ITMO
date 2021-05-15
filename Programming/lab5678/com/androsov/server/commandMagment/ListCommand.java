package com.androsov.server.commandMagment;

public abstract class ListCommand implements Command {
    public String name;
    public String description;
    public String argumentFormat;
    public boolean userAccessible;

    public String getName() {
        return name;
    }
    public abstract String getDescription();
    public boolean isUserAccessible() { return userAccessible; }
    public String getArgumentFormat() { return argumentFormat; }
}
