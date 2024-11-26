package com.BankApp.Bank.services.implementations;

import com.BankApp.Bank.Repository.CustomerWalletRepo;
import com.BankApp.Bank.dtos.requests.CustomerRegisterDto;
import com.BankApp.Bank.models.CustomerWallet;
import com.BankApp.Bank.services.interfaces.CustomerWalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerWalletServiceImpl implements CustomerWalletService {

    private CustomerWalletRepo customerWalletRepo;

    public CustomerWallet findByUsername(String name){
        return customerWalletRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("user already exist"));
    }

    @Override
    public CustomerWallet registerCustomer(CustomerRegisterDto customerRegisterDto) {return null;
    }
}
