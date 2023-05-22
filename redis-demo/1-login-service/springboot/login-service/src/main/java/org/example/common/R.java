package org.example.common;


import lombok.Data;

@Data
public class R <T> {
    private int code; // 200（成功）、400（失败）
    private String message;
    private T data;

    public static <T> R success(T data,String message){
        R<T> r = new R<>();
        r.code=200;
        r.message=message;
        r.data=data;
        return r;
    }

    public static <T> R success(String message){
        R<T> r = new R<>();
        r.code=200;
        r.message=message;
        return r;
    }

    public static <T> R fail(String message){
        R<T> r = new R<>();
        r.code=400;
        r.message=message;
        return r;
    }
}

