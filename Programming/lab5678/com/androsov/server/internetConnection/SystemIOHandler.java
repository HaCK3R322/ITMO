package com.androsov.server.internetConnection;

import java.io.IOException;
import java.util.Scanner;

public class SystemIOHandler implements ServerIO {
    @Override
    public void accept() {

    }

    @Override
    public String getCommandLine() throws IOException {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    @Override
    public void sendResponse(String line) throws IOException {
        System.out.println(line);
    }
}
