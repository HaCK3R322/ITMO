package com.androsov.server.InternetConnection;

import java.io.IOException;

public interface ServerIO {
    void accept();
    String getCommandLine() throws IOException;
    void sendResponse(String line) throws IOException;
}
