package com.BankApp.Bank.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
public class CustomerWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(nullable = false, length = 5)
    private String password;
    private BigDecimal balance;

    @OneToMany(mappedBy = "customerwallet")
    private List<Transaction> transactions;



    public CustomerWallet() {}

    public CustomerWallet(String name, String password, BigDecimal balance, List<Transaction> transactions) {
        this.name = name;
        this.password = password;
        this.balance = balance;
        this.transactions = transactions;
    }

}
