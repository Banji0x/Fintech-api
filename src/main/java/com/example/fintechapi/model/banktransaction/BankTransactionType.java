package com.example.fintechapi.model.banktransaction;

import lombok.Getter;

@Getter
public enum BankTransactionType {
    CREDIT,
    DEBIT,
//    TRANSFER,
    DEPOSIT,
    WITHDRAWAL
}
