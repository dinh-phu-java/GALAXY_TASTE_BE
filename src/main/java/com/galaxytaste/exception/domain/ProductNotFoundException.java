package com.galaxytaste.exception.domain;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message){
        super(message);
    }
}
