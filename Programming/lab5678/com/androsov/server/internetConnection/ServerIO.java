package com.androsov.server.internetConnection;

import com.androsov.general.IO.IO;

import java.nio.ByteBuffer;

public class ServerIO implements IO {
    public boolean hasRequest() {
        return false;
    }

    @Override
    public void send(ByteBuffer buffer) {

    }

    @Override
    public ByteBuffer get() {
        return null;
    }
}
