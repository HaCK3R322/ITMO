package com.androsov.general.request;

import java.util.List;

public interface Request { // == command
    String getCommandName();
    List<Object> getArgs();
}
