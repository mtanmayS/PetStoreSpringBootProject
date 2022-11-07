package com.example.demo.exception;

public class NoDataFoundException extends RuntimeException {

    private String message;

    public NoDataFoundException(){}

    public NoDataFoundException(String message){
        super(message);
        this.message= message;
    }

}
