package com.androsov.general.request;

import com.androsov.general.User;

import java.util.List;

public interface Request { // == command
    User getUser();
    String getCommandName();
    List<Object> getArgs();
}
