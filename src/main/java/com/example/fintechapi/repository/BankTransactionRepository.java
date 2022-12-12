package com.example.fintechapi.repository;

import com.example.fintechapi.model.banktransaction.BankTransaction;
import com.example.fintechapi.model.banktransaction.BankTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
//    Optional<BankTransaction> findBankTransactionById(Long id);

    List<BankTransaction> findBankTransactionsByReceiverAccountNumber(String accountNumber);

    List<BankTransaction> findBankTransactionsBySenderAccountNumber(String accountNumber);

    List<BankTransaction> findBankTransactionsBySenderAccountNumberAndBankTransactionType(String accountNumber, BankTransactionType bankTransactionType);

    List<BankTransaction> findBankTransactionsByReceiverAccountNumberAndBankTransactionType(String accountNumber, BankTransactionType bankTransactionType);
}