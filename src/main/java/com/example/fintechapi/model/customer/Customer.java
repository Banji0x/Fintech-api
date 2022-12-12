package com.example.fintechapi.model.customer;

import com.example.fintechapi.model.bankaccount.BankAccount;
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
public class Customer {
    @Id @GeneratedValue @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String dateOfBirth;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @Column(nullable = false,unique = true)
    private String emailAddress;

    @Column(nullable = false)
    private String homeAddress;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "customer")
    private List<BankAccount> listOfAccounts = new ArrayList<>();

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdOn;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column
    private Date updatedOn;

    public Customer() {
    }
}