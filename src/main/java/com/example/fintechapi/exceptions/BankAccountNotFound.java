package com.example.fintechapi.exceptions;

public class BankAccountNotFound extends RuntimeException {
    public BankAccountNotFound(String element) {
        super("The" + element + "provided is invalid");
    }
}
