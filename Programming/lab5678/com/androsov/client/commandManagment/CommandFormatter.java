package com.androsov.client.commandManagment;

import java.util.*;

public class CommandFormatter {
    public static HashMap<String, Class> toClassMap;
    HashMap<String, LinkedList<Class>> commandsArguments;


    /**
     * Создаёт на основе ответа от сервера набор команд и аргументов, которые можно использовать с этими командами
     * @param commandsArguments
     */
    public CommandFormatter(String commandsArguments) {
        this.commandsArguments = new HashMap<>();

        toClassMap = new HashMap<>();
        toClassMap.put("void", null);
        toClassMap.put("String", String.class);

        toClassMap.put("Integer", Long.class);
        toClassMap.put("Long", Long.class);

        toClassMap.put("Double", Double.class);
        toClassMap.put("Float", Double.class);

        String[] arrayOfCommands = commandsArguments.split("\n");

        for(int i = 0; i < arrayOfCommands.length; i++) {
            String commandName = arrayOfCommands[i].split(" ")[0];
            String[] commandArguments = arrayOfCommands[i].split(" ")[1].split("\\|");
            try {
                LinkedList<Class> commandArgumentsClassRepresentation = new LinkedList<>(); // создаем лист для перевода из String формы в Class
                for(int j = 0; j < commandArguments.length; j++) {
                    commandArgumentsClassRepresentation.add(toClassMap.get(commandArguments[j])); // добавляем
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

    public String getName(String commandLine) {
        return commandLine.split(" ")[0];
    }

    public List<Object> getArgs(String commandLine) {
        final List<Object> args = new LinkedList<>();

        String[] splattedCommandLine = commandLine.split(" ");

        int numberOfArgs = splattedCommandLine.length - 1;

        if (numberOfArgs > 0) {
            String[] argsStringFormat = Arrays.copyOfRange(splattedCommandLine, 1, splattedCommandLine.length - 1);
            for (int i = 0; i < argsStringFormat.length; i++) {
                args.add(getObjectForm(argsStringFormat[i]));
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
