package com.example.fintechapi.exceptions;

public class InCorrectCredentialsException extends RuntimeException {
    public InCorrectCredentialsException() {
        super("Incorrect credentials");
    }
}
