package com.example.fintechapi.model.bankaccount.dto;

public record BankTransferDto(String senderAccountNo, String receiverAccountNo,Double amount,String description) {
}
