package com.fortwelve.wechatstore.util;

public class OrderException extends Exception {
    private int code;
    public OrderException(String message,int code) {
        super(message);
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}
