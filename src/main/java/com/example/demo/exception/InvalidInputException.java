package com.example.demo.exception;

public class InvalidInputException extends RuntimeException {

    private String message;

    public InvalidInputException(){}

    public InvalidInputException(String message){
        super(message);
        this.message= message;
    }

}
