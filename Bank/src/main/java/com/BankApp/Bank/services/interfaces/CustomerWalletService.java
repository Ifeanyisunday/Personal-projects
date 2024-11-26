package com.BankApp.Bank.services.interfaces;

import com.BankApp.Bank.dtos.requests.CustomerRegisterDto;
import com.BankApp.Bank.models.CustomerWallet;

public interface CustomerWalletService {

    CustomerWallet registerCustomer(CustomerRegisterDto customerRegisterDto);
}
