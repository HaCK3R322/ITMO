package com.androsov.general.response;

import java.util.List;

public class ResponseImpl implements Response {
    String message;
    List<Object> data;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String str) {
        message = str;
    }

    @Override
    public List<Object> getData() {
        return data;
    }

    @Override
    public void setData(List<Object> data) {
        this.data = data;
    }

    @Override
    public void addData(Object obj) {
        data.add(obj);
    }
}
