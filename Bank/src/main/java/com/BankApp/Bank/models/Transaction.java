package com.BankApp.Bank.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private String type;
    private TransactionStatus status;
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "customerwallet_id")
    private CustomerWallet customerWallet;



}
