package com.BankApp.Bank.Repository;

import com.BankApp.Bank.models.CustomerWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerWalletRepo extends JpaRepository<CustomerWallet, Long> {

    Optional<CustomerWallet> findByName(String name);
}
