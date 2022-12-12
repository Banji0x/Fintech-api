package com.example.fintechapi.exceptions;

public class InsufficientFunds extends RuntimeException{
    public InsufficientFunds() {
        super(" Insufficient Funds !!!");
    }
}
