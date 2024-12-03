package com.BankApp.Bank.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.security.core.GrantedAuthority;

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

    @Transient
    private Collection<? extends GrantedAuthority> authorities;


    public Customer() {}

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.acctNo = generateAcctNo();
        this.balance = BigDecimal.ZERO;
    }

    private String generateAcctNo(){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder createAcctNo = new StringBuilder();

        for(int i = 0; i < 5; i++){
            secureRandom.nextInt(10);
            createAcctNo.append(secureRandom);
        }
        return createAcctNo.toString();
    }


}
