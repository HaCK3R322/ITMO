package com.androsov.general.request;

import com.androsov.general.User;

import java.io.Serializable;
import java.util.List;

public interface Request extends Serializable { // == command
    User getUser();
    String getCommandName();
    List<Object> getArgs();
}
