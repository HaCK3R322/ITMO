package com.androsov.client.InternetConnection;

import java.io.IOException;

public interface ClientIO {
    void connectToServer(String ipAddress, int port) throws IOException;
    void sendCommandLine(String line) throws IOException;
    String getResponse() throws IOException;
}
