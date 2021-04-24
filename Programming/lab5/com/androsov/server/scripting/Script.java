package com.androsov.server.scripting;

import com.androsov.server.productManagment.exceptions.SelfCycledScriptChainException;

import java.io.*;
import java.util.*;

/**
 * Script class that contains user-like input and some other stuff
 */
public class Script {
    public Script(String filePath) throws IOException, SelfCycledScriptChainException {
        try {
            commands = readScriptFromFile(filePath);
            if(checkSelfCycling(commands, new HashSet<>()))
                throw new SelfCycledScriptChainException("Script contains self-cycled chain of scripts.");
            nextLine = "";
            commandLine = 0;
            this.filePath = filePath;
        } catch (IOException e) {
            throw e;
        }
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

    public String getCommand() {
        if(commandLine < commands.size() - 1) {
            nextLine = commands.get(commandLine + 1);
        } else {
            nextLine = "";
        }
        return commands.get(commandLine++);
    }

    public void moveCommandLine(int shift) {
        commandLine += shift;
    }

    public static List<String> readScriptFromFile(String filePath) throws IOException {

        List<String> scriptCommands = new ArrayList<>();

        try {
            FileReader reader = new FileReader(new File(filePath));

            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while((line = bufferedReader.readLine()) != null) {
                scriptCommands.add(line);
            }

        } catch (Exception e) {
            throw e;
        }

        return scriptCommands;
    }

    public boolean checkSelfCycling(List<String> scriptCommands, Set<String> previousNames) throws IOException {
        boolean cycled = false;

        List<String> singleScriptReferences = new LinkedList<>();
        singleScriptReferences.addAll(getSingleScriptReferences(scriptCommands));

        if(!(singleScriptReferences.size() == 0)) {

            if(previousNames.add(filePath)) {
                for(int i = 0; i < singleScriptReferences.size(); i++) {
                    try {
                        if(checkSelfCycling(readScriptFromFile(singleScriptReferences.get(i)), previousNames)) {
                            cycled = true;
                        }
                    } catch (IOException e) {
                        throw e;
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
        Set<String> single = new HashSet<>();

        //парсим имена всех скриптов
        for(int i = 0; i < commandLines.size(); i++) {
            if(commandLines.get(i).split(" ")[0].equals("execute_script")) {     // разделяем строку на слова, смотрим равно ли первое слово execute_script
                if(commandLines.get(i).split(" ").length > 1) {                 // смотрим есть ли имя у скрипта
                    non_single.add(commandLines.get(i).split(" ")[1]);          // добавляем в список всех ссылок на скрипты
                }
            }
        }

        //добавляем их в сет, делаем из сета лист
        single.addAll(non_single);


        return single;
    }
}
