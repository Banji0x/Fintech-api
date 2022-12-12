package com.example.fintechapi.model.banktransaction;

import lombok.Getter;

@Getter
public enum BankTransactionStatus {
    SUCCESS,
    PENDING,
    FAILED,
    REVERSED
}
