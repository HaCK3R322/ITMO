package com.androsov.server.CommandMagment;

public class CommandFormatter {
    public static String getName(String commandLine) {
        return commandLine.split(" ")[0];
    }

    public static String[] getArgs(String commandLine) {
        String[] args = new String[commandLine.split(" ").length - 1];
        for(int i = 0; i < args.length; i++) {
            args[i] = commandLine.split(" ")[i+1];
        }

        return args;
    }
}
