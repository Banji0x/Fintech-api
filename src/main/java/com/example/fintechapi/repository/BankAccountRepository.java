package com.example.fintechapi.repository;

import com.example.fintechapi.model.bankaccount.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    //Bank accounts
//    Optional<BankAccount> findBankAccountById(Long bankAccountId); Crud repository provides this already...
    Optional<BankAccount> findBankAccountByAccountNumber(String accountNumber);
    Optional<BankAccount> findBankAccountByCustomerFullName(String fullName);
    Optional<BankAccount> findBankAccountByCustomerPhoneNumber(String phoneNumber);
    Optional<BankAccount> findBankAccountByCustomerEmailAddress(String emailAddress);
    //Bank accounts
    List<BankAccount> findBankAccountsByCustomerId(Long customerId);
    List<BankAccount> findBankAccountsByCustomerFullName(String fullName);
    List<BankAccount> findBankAccountsByCustomerPhoneNumber(String phoneNumber);
    List<BankAccount> findBankAccountsByCustomerEmailAddress(String emailAddress);
}
