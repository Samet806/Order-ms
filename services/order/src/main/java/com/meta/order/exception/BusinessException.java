package com.meta.order.exception;

public class BusinessException  extends  RuntimeException{
    private String message;
    public BusinessException(String message){
        super(message);
    }
}
