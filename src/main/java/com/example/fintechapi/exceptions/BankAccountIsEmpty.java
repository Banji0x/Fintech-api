package com.example.fintechapi.exceptions;

public class BankAccountIsEmpty extends RuntimeException {
    public BankAccountIsEmpty(){
        super("No BankAccount found");
    }

}
