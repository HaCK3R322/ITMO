package com.androsov.general.response;

import com.androsov.general.User;

import java.util.List;

public interface Response {
    String getMessage();
    void setMessage(String str);

    List<Object> getData();
    void setData(List<Object> data);
    void addData(Object obj);

    User getUser();
}
