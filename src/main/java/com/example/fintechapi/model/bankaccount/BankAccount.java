package com.example.fintechapi.model.bankaccount;

import com.example.fintechapi.model.customer.Customer;
import com.example.fintechapi.model.banktransaction.BankTransaction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class BankAccount {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false,updatable = false)
    private Customer customer;

    @Column(nullable = false, updatable = false, length = 10)
    private String accountNumber;

    @Column(nullable = false)
    private Double accountBalance;

    @Column(nullable = false, length = 4)
    private String accountPin;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BankAccountType bankAccountType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BankAccountStatus bankAccountStatus;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Date> loginHistory = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankAccount_id", referencedColumnName = "id")
    private List<BankTransaction> listOfBankTransactions = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column
    @Temporal(value = TemporalType.TIMESTAMP)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Date> updatedOn = new ArrayList<>();

    public BankAccount() {
    }

}