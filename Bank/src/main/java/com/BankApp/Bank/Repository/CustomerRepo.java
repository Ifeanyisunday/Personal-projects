package com.BankApp.Bank.Repository;

import com.BankApp.Bank.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByAcctNo(String acctNo);
}
