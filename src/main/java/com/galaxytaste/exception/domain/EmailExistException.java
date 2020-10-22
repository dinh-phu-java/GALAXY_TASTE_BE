package com.galaxytaste.exception.domain;

public class EmailExistException extends Exception{
    public EmailExistException(String message) {
        super(message);
    }
}
