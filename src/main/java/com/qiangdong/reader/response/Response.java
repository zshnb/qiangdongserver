package com.qiangdong.reader.response;

/**
 * A type parameter Response contains data and message which will be convert to json in http response
 * */
public class Response<T> {
    private T data;

    private String message;

    public static Response<String> ok() {
        return new Response<>();
    }

    public static <T> Response<T> ok(T data) {
        Response<T> response = new Response<>();
        response.data = data;
        return response;
    }

    public static Response<String> error(String message) {
        Response<String> response = new Response<>();
        response.message = message;
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
