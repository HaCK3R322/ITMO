package com.androsov.client.userInterface;

import java.io.IOException;

public interface Ui {
    String getCommand();
    void sendResponse(String line);
    boolean askReconnect();
    void init() throws IOException;
}
