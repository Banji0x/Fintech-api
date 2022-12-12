package com.example.fintechapi.service;

import com.example.fintechapi.repository.BankAccountRepository;
import com.example.fintechapi.exceptions.*;
import com.example.fintechapi.model.customer.Customer;
import com.example.fintechapi.model.bankaccount.BankAccount;
import com.example.fintechapi.model.bankaccount.BankAccountStatus;
import com.example.fintechapi.model.bankaccount.BankAccountType;
import com.example.fintechapi.model.bankaccount.dto.*;
import com.example.fintechapi.model.banktransaction.BankTransaction;
import com.example.fintechapi.model.banktransaction.BankTransactionStatus;
import com.example.fintechapi.model.banktransaction.BankTransactionType;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final PasswordEncoder bcryptEncoder;
//    private final CustomerRepository customerRepository;


    public BankAccountService(BankAccountRepository bankAccountRepository, PasswordEncoder bcryptEncoder/*, CustomerRepository customerRepository */) {
        this.bankAccountRepository = bankAccountRepository;
        this.bcryptEncoder = bcryptEncoder;
//        this.customerRepository = customerRepository;
    }

    // Find BankAccount
    public BankAccount findBankAccountById(Long bankAccountId) {
        return bankAccountRepository
                .findById(bankAccountId)
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(bankAccountId.toString());
                });
    }

    public BankAccount findBankAccountByAccountNumber(String accountNumber) {
        return bankAccountRepository
                .findBankAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(accountNumber);
                });
    }

    public BankAccount findBankAccountByCustomerFullName(String fullName) {
        return bankAccountRepository
                .findBankAccountByCustomerFullName(fullName)
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(fullName);
                });
    }

    public BankAccount findBankAccountByCustomerPhoneNumber(String phoneNumber) {
        return bankAccountRepository
                .findBankAccountByCustomerPhoneNumber(phoneNumber)
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(phoneNumber);
                });
    }

    public BankAccount findBankAccountByCustomerEmailAddress(String emailAddress) {
        return bankAccountRepository
                .findBankAccountByCustomerEmailAddress(emailAddress)
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(emailAddress);
                });
    }

    public Iterable<BankAccount> findAllBankAccounts() {
        var bankAccounts = bankAccountRepository.findAll();
        if (bankAccounts.isEmpty())
            throw new BankAccountIsEmpty();
        return bankAccounts;
    }

    public Iterable<BankAccount> findBankAccountsByCustomerId(Long customerId) {
        var bankAccounts = bankAccountRepository
                .findBankAccountsByCustomerId(customerId);
        if (bankAccounts.isEmpty())
            throw new BankAccountIsEmpty();
        return bankAccounts;
    }

    public Iterable<BankAccount> findBankAccountsByCustomerFullName(String fullName) {
        var bankAccounts = bankAccountRepository
                .findBankAccountsByCustomerFullName(fullName);
        if (bankAccounts.isEmpty())
            throw new BankAccountIsEmpty();
        return bankAccounts;
    }

    public Iterable<BankAccount> findBankAccountsByCustomerPhoneNumber(String phoneNumber) {
        var bankAccounts = bankAccountRepository
                .findBankAccountsByCustomerPhoneNumber(phoneNumber);
        if (bankAccounts.isEmpty())
            throw new BankAccountIsEmpty();
        return bankAccounts;
    }

    public Iterable<BankAccount> findBankAccountsByCustomerEmailAddress(String emailAddress) {
        var bankAccounts = bankAccountRepository
                .findBankAccountsByCustomerEmailAddress(emailAddress);
        if (bankAccounts.isEmpty())
            throw new BankAccountIsEmpty();
        return bankAccounts;
    }

    //get withdrawal history
    //Create BankAccount
    @Transactional
    public CreatedBankAccountDto createBankAccount(BankAccountCreationDto bankAccountCreationDto) {
        var fullName = bankAccountCreationDto.fullName();
        var dateOfBirth = bankAccountCreationDto.dateOfBirth();
        var phoneNumber = bankAccountCreationDto.phoneNumber();
        var emailAddress = bankAccountCreationDto.emailAddress();
        var homeAddress = bankAccountCreationDto.homeAddress();
        var accountPin = this.bcryptEncoder.encode(bankAccountCreationDto.accountPin());
        var bankAccountType = bankAccountCreationDto.bankAccountType();
        var date = Date.from(Instant.now());

        Customer customer = new Customer();
        customer.setFullName(fullName);
        var accountNumber = (long) (Math.random() * 1_000_000);
        customer.setDateOfBirth(dateOfBirth);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmailAddress(emailAddress);
        customer.setHomeAddress(homeAddress);
        customer.setCreatedOn(date);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setCustomer(customer);
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setAccountBalance(0.0);
        bankAccount.setAccountPin(accountPin);
        bankAccount.setBankAccountType(bankAccountType);
        bankAccount.setBankAccountStatus(BankAccountStatus.ACTIVE);
        bankAccount.setCreatedOn(date);
        bankAccountRepository.save(bankAccount);

        return new CreatedBankAccountDto(fullName, accountNumber, bankAccountType);
    }

    //Transfer money
    @Transactional
    public BankTransferDto transferMoney(BankTransferDto bankTransferDto) {
        var senderBankAccount = bankAccountRepository
                .findBankAccountByAccountNumber(bankTransferDto.senderAccountNo())
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(bankTransferDto.senderAccountNo());
                });

        var receiverBankAccount = bankAccountRepository
                .findBankAccountByAccountNumber(bankTransferDto.receiverAccountNo())
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(bankTransferDto.receiverAccountNo());
                });

        if (senderBankAccount.getBankAccountStatus().equals(BankAccountStatus.DISABLED) || receiverBankAccount.getBankAccountStatus().equals(BankAccountStatus.DISABLED))
            throw new DisabledBankAccount();

        var senderAccountBalance = senderBankAccount.getAccountBalance();
        var receiverAccountBalance = receiverBankAccount.getAccountBalance();
        var amountToBeTransferred = bankTransferDto.amount();
        var description = bankTransferDto.description();
        var date = Date.from(Instant.now());

        if (senderAccountBalance < amountToBeTransferred)
            throw new InsufficientFunds();

        //set login date for sender
        senderBankAccount.getLoginHistory().add(date);

        // deduction..
        senderBankAccount.setAccountBalance(senderAccountBalance - amountToBeTransferred);
        receiverBankAccount.setAccountBalance(receiverAccountBalance + amountToBeTransferred);

        //transactions
        var senderTransaction = new BankTransaction();
        var receiverTransaction = new BankTransaction();

        //sender transactions
        senderTransaction.setAmount(amountToBeTransferred);
        senderTransaction.setSenderAccountNumber(bankTransferDto.senderAccountNo());
        senderTransaction.setReceiverAccountNumber(bankTransferDto.receiverAccountNo());
        senderTransaction.setDescription(description);
        senderTransaction.setBankTransactionType(BankTransactionType.DEBIT);
        senderTransaction.setBankTransactionStatus(BankTransactionStatus.SUCCESS);
        senderTransaction.setDateOfTransaction(date);

        //receiver transactions
        receiverTransaction.setAmount(amountToBeTransferred);
        receiverTransaction.setSenderAccountNumber(bankTransferDto.senderAccountNo());
        receiverTransaction.setReceiverAccountNumber(bankTransferDto.receiverAccountNo());
        receiverTransaction.setDescription(description);
        receiverTransaction.setBankTransactionType(BankTransactionType.CREDIT);
        receiverTransaction.setBankTransactionStatus(BankTransactionStatus.SUCCESS);
        receiverTransaction.setDateOfTransaction(date);

        //add transactions
        senderBankAccount.getListOfBankTransactions().add(senderTransaction);
        receiverBankAccount.getListOfBankTransactions().add(receiverTransaction);

        bankAccountRepository.saveAll(List.of(senderBankAccount, receiverBankAccount));

        return bankTransferDto;
    }

    // deposit money
    @Transactional
    public BankDepositDto depositMoney(BankDepositDto bankDepositDto) {
        String accountNumber = bankDepositDto.receiverAccountNumber();
        var amount = bankDepositDto.amount();

        BankAccount bankAccount = bankAccountRepository
                .findBankAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(accountNumber);
                });

        if (bankAccount.getBankAccountStatus().equals(BankAccountStatus.DISABLED))
            throw new DisabledBankAccount();

        Double currentAccountBalance = bankAccount.getAccountBalance();
        bankAccount.setAccountBalance(currentAccountBalance + amount);

        bankAccountRepository.save(bankAccount);
        return bankDepositDto;
    }

    //withdraw money
    @Transactional
    public BankWithdrawalDto withdrawMoney(BankWithdrawalDto bankWithdrawalDto) {
        var accountNumber = bankWithdrawalDto.accountNumber();
        var amount = bankWithdrawalDto.amount();


        BankAccount bankAccount = bankAccountRepository
                .findBankAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(accountNumber);
                });

        if (bankAccount.getBankAccountStatus().equals(BankAccountStatus.DISABLED))
            throw new DisabledBankAccount();

        if (bankAccount.getAccountBalance() < amount)
            throw new InsufficientFunds();

        var currentBalance = bankAccount.getAccountBalance();
        bankAccount.setAccountBalance(currentBalance - amount);

        bankAccountRepository.save(bankAccount);
        return bankWithdrawalDto;
    }

    //change pin
    @Transactional
    public void changePin(String oldPin, String newPin, String accountNumber) {

        BankAccount bankAccount = bankAccountRepository
                .findBankAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(accountNumber);
                });

        if (bankAccount.getBankAccountStatus().equals(BankAccountStatus.DISABLED))
            throw new DisabledBankAccount();

        String encryptedPin = bankAccount.getAccountPin();
        boolean matches = bcryptEncoder.matches(oldPin, encryptedPin);

        if (matches)
            bankAccount.setAccountPin(bcryptEncoder.encode(newPin));
        else
            throw new InCorrectCredentialsException();
        bankAccountRepository.save(bankAccount);
    }

    //upgrade account type
    @Transactional
    public void upgradeAccountType(String accountNumber, BankAccountType newBankAccountType) {

        BankAccount bankAccount = bankAccountRepository
                .findBankAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(accountNumber);
                });

        if (bankAccount.getBankAccountStatus().equals(BankAccountStatus.DISABLED))
            throw new DisabledBankAccount();

        bankAccount.setBankAccountType(newBankAccountType);
        bankAccountRepository.save(bankAccount);
    }

    //change account status ---- admin only
    @Transactional
    public void changeAccountStatus(String accountNumber, BankAccountStatus newBankAccountStatus) {
        BankAccount bankAccount = bankAccountRepository
                .findBankAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> {
                    throw new BankAccountNotFound(accountNumber);
                });

        bankAccount.setBankAccountStatus(newBankAccountStatus);
        bankAccountRepository.save(bankAccount);
    }

}