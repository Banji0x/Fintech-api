package com.example.fintechapi.exceptions;

public class DisabledBankAccount extends RuntimeException {
    public DisabledBankAccount() {
        super("A disabled bank account was provided");
    }
}
