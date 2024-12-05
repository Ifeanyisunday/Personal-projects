package com.BankApp.Bank.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.boot.autoconfigure.web.WebProperties;


import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Setter
@Getter
public class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(nullable = false, length = 5)
    private String password;
    private String acctNo;
    private BigDecimal balance;

    @OneToMany(mappedBy = "customer")
    private List<Transaction> transactions;




    public Customer() {}

    public Customer(String username, String password, String acctNo, BigDecimal balance, List<Transaction> transactions) {
        this.username = username;
        this.password = password;
        this.acctNo = acctNo;
        this.balance = balance;
        this.transactions = transactions;
    }

}
