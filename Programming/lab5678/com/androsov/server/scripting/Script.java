package com.androsov.server.scripting;

import com.androsov.server.productManagment.exceptions.SelfCycledScriptChainException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Script class that contains user-like input and some other stuff
 */
public class Script {
    public Script(String filePath) throws IOException, SelfCycledScriptChainException {
        commands = readScriptFromFile(filePath);
        if(checkSelfCycling(commands, new HashSet<>()))
            throw new SelfCycledScriptChainException("Script contains self-cycled chain of scripts.");
        nextLine = "";
        commandLine = 0;
        this.filePath = filePath;
    }

    public Script(String filePath, List<String> commands) {
        this.commands = commands;
        nextLine = "";
        commandLine = 0;
        this.filePath = filePath;
    }

    public List<String> commands;
    public String nextLine;
    public int commandLine;
    public String filePath;

    public int getSize() {
        return commands.size();
    }

    public String takeCommand() {
        if(commandLine < commands.size() - 1) {
            nextLine = commands.get(commandLine + 1);
        } else {
            nextLine = "";
        }
        return commands.get(commandLine++);
    }

    public static List<String> readScriptFromFile(String filePath) throws IOException {

        List<String> scriptCommands = new ArrayList<>();

        FileReader reader = new FileReader(new File(filePath));

        BufferedReader bufferedReader = new BufferedReader(reader);

        String line;
        while((line = bufferedReader.readLine()) != null) {
            scriptCommands.add(line);
        }

        bufferedReader.close();
        reader.close();

        return scriptCommands;
    }

    public boolean checkSelfCycling(List<String> scriptCommands, Set<String> previousNames) throws IOException {
        boolean cycled = false;

        List<String> singleScriptReferences = new LinkedList<>(getSingleScriptReferences(scriptCommands));

        if(!(singleScriptReferences.size() == 0)) {

            if(previousNames.add(filePath)) {
                for (String singleScriptReference : singleScriptReferences) {
                    if (checkSelfCycling(readScriptFromFile(singleScriptReference), previousNames)) {
                        cycled = true;
                    }
                }
            } else {
                cycled = true;
            }

        }

        return cycled;
    }

    protected static Set<String> getSingleScriptReferences(List<String> commandLines) {
        List<String> non_single = new LinkedList<>();

        //парсим имена всех скриптов
        for (String line : commandLines) {
            if (line.split(" ")[0].equals("execute_script")) {     // разделяем строку на слова, смотрим равно ли первое слово execute_script
                if (line.split(" ").length > 1) {                 // смотрим есть ли имя у скрипта
                    non_single.add(line.split(" ")[1]);          // добавляем в SET всех ссылок на скрипты
                }
            }
        }


        return new HashSet<>(non_single);
    }
}
