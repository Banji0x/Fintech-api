package com.example.fintechapi.model.banktransaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class BankTransaction {
    @Id @GeneratedValue @Column(updatable = false)
    private Long id;

    @Column(nullable = false,updatable = false)
    private Double amount;

    @Column(nullable = false, updatable = false)
    private String senderAccountNumber;

    @Column(nullable = false, updatable = false)
    private String receiverAccountNumber;

    @Column(nullable = false, updatable = false)
    private String description;

    @Column(nullable = false, updatable = false)
    private BankTransactionType bankTransactionType;

    @Column(nullable = false)
    private BankTransactionStatus bankTransactionStatus;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date dateOfTransaction;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Date> transactionUpdateHistory = new ArrayList<>() ;

    public BankTransaction() {
    }

}