package com.example.fintechapi.model.bankaccount.dto;

import com.example.fintechapi.model.bankaccount.BankAccountType;

public record CreatedBankAccountDto(String fullname, Long accountNumber, BankAccountType bankAccountType) {
}
