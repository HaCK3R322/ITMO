package com.androsov.server.InternetConnection;

import java.io.IOException;
import java.util.Scanner;

public class SystemIOHandler implements ServerIO {
    @Override
    public void accept() {

    }

    @Override
    public String getCommandLine() throws IOException {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        return line;
    }

    @Override
    public void sendResponse(String line) throws IOException {
        System.out.println(line);
    }
}
