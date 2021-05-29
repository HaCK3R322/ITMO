package com.androsov.server.internetConnection;

import com.androsov.general.IO.IO;
import com.androsov.general.User;
import com.androsov.general.request.Request;
import com.androsov.general.response.Response;

import java.nio.ByteBuffer;

public class ServerIO implements IO {
    private User currentUser;

    public boolean hasRequest() {
        return false;
    }

    public void setUser(User user) {
        currentUser = user;
    }

    @Override
    public void send(ByteBuffer buffer) {
        //send response to current user
    }

    @Override
    public ByteBuffer get() {
        return null;
    }
}
