package com.androsov.server.commandMagment;

import com.androsov.server.productManagment.exceptions.CommandBuildError;

import java.lang.reflect.Field;

public class CommandStructureChecker {
    public static void check(Command command) throws CommandBuildError {
        Class commandClass = command.getClass();
        Class<?> current = command.getClass();
        while(current.getSuperclass() != null){
            for(Field field : current.getDeclaredFields()) {
                try {
                    if (field.get(command) == null) throw new CommandBuildError("In command " + commandClass.getName() + " field \"" + field.getName() + "\" is null!");
                } catch (IllegalAccessException e) {

                }
            }
            current = current.getSuperclass();
        }
    }
}
