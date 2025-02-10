package com.example.exception;

public class UsernamePasswordException extends RuntimeException{

    public UsernamePasswordException(String message){
        super(message);
    }
}
