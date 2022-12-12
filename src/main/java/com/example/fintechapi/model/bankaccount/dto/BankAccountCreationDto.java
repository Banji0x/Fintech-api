package com.example.fintechapi.model.bankaccount.dto;

import com.example.fintechapi.model.bankaccount.BankAccountType;

 public record BankAccountCreationDto(String fullName, String dateOfBirth, String phoneNumber, String emailAddress, String homeAddress, String accountPin, BankAccountType bankAccountType) {
}
