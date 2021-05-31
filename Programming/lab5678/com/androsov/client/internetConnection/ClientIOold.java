package com.androsov.client.internetConnection;

import java.io.IOException;

public interface ClientIOold {
    void connectToServer(String ipAddress, int port) throws IOException;
    void sendCommandLine(String line) throws IOException;
    String getResponse() throws IOException;
}
