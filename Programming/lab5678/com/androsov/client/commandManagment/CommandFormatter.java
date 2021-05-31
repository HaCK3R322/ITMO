package com.androsov.client.commandManagment;

import java.util.*;

public class CommandFormatter {
    public static HashMap<String, Class> toClassMap;
    final HashMap<String, LinkedList<Class>> commandsArguments;


    /**
     * Создаёт на основе ответа от сервера набор команд и аргументов, которые можно использовать с этими командами
     * @param commandsArguments
     */
    public CommandFormatter(String commandsArguments) { // переделать под чтение респонса
        this.commandsArguments = new HashMap<>();

        toClassMap = new HashMap<>();
        toClassMap.put("void", null);
        toClassMap.put("String", String.class);

        toClassMap.put("Integer", Long.class);
        toClassMap.put("Long", Long.class);

        toClassMap.put("Double", Double.class);
        toClassMap.put("Float", Double.class);

        String[] arrayOfCommands = commandsArguments.split("\n");

        for (String arrayOfCommand : arrayOfCommands) {
            String commandName = arrayOfCommand.split(" ")[0];
            String[] commandArguments = arrayOfCommand.split(" ")[1].split("\\|");
            try {
                LinkedList<Class> commandArgumentsClassRepresentation = new LinkedList<>(); // создаем лист для перевода из String формы в Class
                for (String commandArgument : commandArguments) {
                    commandArgumentsClassRepresentation.add(toClassMap.get(commandArgument)); // добавляем
                }
                this.commandsArguments.put(commandName, commandArgumentsClassRepresentation); // запихиваем в мапу
            } catch (NullPointerException e) {
                System.out.println("Command wrong argument on command " + commandName);
            }
        }
    }

    /**
     * смотрит есть ли такая команда в доступных пользователю, затем определяет тип её аргумента и ищет его в списке доступных аргуменотов для этой команды
     * @param commandLine
     * @return
     */
    public boolean isValid(String commandLine) {
        String commandName = commandLine.split(" ")[0];
        String commandArgument = commandLine.split(" ").length > 1 ? commandLine.split(" ")[1] : "";

        if(commandsArguments.containsKey(commandName)) {
            boolean argumentValid = false;

            for(int i = 0; i < commandsArguments.get(commandName).size(); i++) {
                if(commandsArguments.get(commandName).get(i) == getClass(commandArgument))
                    argumentValid = true;
            }

            return argumentValid;
        } else {
            return false;
        }
    }

    public int getLength(String commandLine) {
        return commandLine.split(" ").length;
    }

    public String extractName(String commandLine) {
        return commandLine.split(" ")[0];
    }

    public List<Object> extractArgs(String commandLine) {
        final List<Object> args = new LinkedList<>();

        String[] splattedCommandLine = commandLine.split(" ");

        int numberOfArgs = splattedCommandLine.length - 1;

        if (numberOfArgs > 0) {
            String[] argsStringFormat = Arrays.copyOfRange(splattedCommandLine, 1, splattedCommandLine.length - 1);
            for (String s : argsStringFormat) {
                args.add(getObjectForm(s));
            }
        }

        return args;
    }

    protected static Object getObjectForm(String arg) {
        Class argClass = getClass(arg);
        if (argClass.equals(Long.class))
            return Long.parseLong(arg);
        else if (argClass.equals(Double.class))
            return Double.parseDouble(arg);
        else return arg;
    }

    /**
     * возвращает подходящий тип класса для данного аргумента
     * @param value
     * @return
     */
    protected static Class getClass(String value) {
        try(Scanner sc = new Scanner(value)) {
            if (sc.hasNextLong())
                return Long.class;
            else if (sc.hasNextDouble())
                return Double.class;
            else if(sc.hasNextLine())
                return String.class;
            else return null;
        }
    }
}
