package com.androsov.server.commandMagment;

import com.androsov.general.request.Request;
import com.androsov.general.response.Response;

public interface Command {
    Response execute(Request request);
    String getName();
    String getDescription();
    String getArgumentFormat();
    boolean isUserAccessible();
}
