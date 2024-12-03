package com.BankApp.Bank.Repository;

import com.BankApp.Bank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCustomerId(Long customerId);
}
