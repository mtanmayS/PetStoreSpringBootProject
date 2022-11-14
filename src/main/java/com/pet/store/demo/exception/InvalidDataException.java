package com.pet.store.demo.exception;

public class InvalidDataException extends RuntimeException {

    private String message;

    public InvalidDataException(){}

    public InvalidDataException(String message){
        super(message);
        this.message= message;
    }

}
