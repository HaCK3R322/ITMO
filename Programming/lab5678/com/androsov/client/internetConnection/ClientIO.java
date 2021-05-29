package com.androsov.client.internetConnection;

import com.androsov.general.IO.IO;

import java.io.IOException;
import java.nio.ByteBuffer;

public class ClientIO implements IO {
    public void connectToServer(String serverIP, int serverPort) throws IOException {

    }

    @Override
    public void send(ByteBuffer buffer) {

    }

    @Override
    public ByteBuffer get() {
        return null;
    }
}
