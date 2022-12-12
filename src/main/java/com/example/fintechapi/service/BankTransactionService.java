package com.example.fintechapi.service;

import com.example.fintechapi.exceptions.TransactionNotFound;
import com.example.fintechapi.model.banktransaction.BankTransaction;
import com.example.fintechapi.repository.BankTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionService {
    private final BankTransactionRepository bankTransactionRepository;

    public BankTransactionService(BankTransactionRepository bankTransactionRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
    }

    public BankTransaction findTransactionById(Long id) {
        return bankTransactionRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new TransactionNotFound();
                });
    }

}
