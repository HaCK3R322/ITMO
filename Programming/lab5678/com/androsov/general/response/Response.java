package com.androsov.general.response;

import java.util.List;

public interface Response {
    String getMessage();
    void setMessage(String str);

    List<Object> getData();
    void setData(List<Object> data);
    void addData(Object obj);


}
