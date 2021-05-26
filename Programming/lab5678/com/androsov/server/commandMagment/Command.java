package com.androsov.server.commandMagment;

import com.androsov.general.response.Response;

import java.util.List;

public interface Command {
    Response execute(List<Object> args);
    String getName();
    String getDescription();
    String getArgumentFormat();
    boolean isUserAccessible();
}
