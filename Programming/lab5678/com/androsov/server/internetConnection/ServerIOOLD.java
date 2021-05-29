package com.androsov.server.internetConnection;

import java.io.IOException;

public interface ServerIOOLD {
    void accept();
    String getCommandLine() throws IOException;
    void sendResponse(String line) throws IOException;
}
