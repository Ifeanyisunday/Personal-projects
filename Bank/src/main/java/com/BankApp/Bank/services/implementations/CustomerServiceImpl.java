package com.BankApp.Bank.services.implementations;

import com.BankApp.Bank.Repository.CustomerRepo;
import com.BankApp.Bank.Repository.TransactionRepo;
import com.BankApp.Bank.dtos.requests.CustomerRegisterDto;
import com.BankApp.Bank.exceptions.AllUserTypeException;
import com.BankApp.Bank.models.Customer;
import com.BankApp.Bank.models.Transaction;
import com.BankApp.Bank.models.TransactionStatus;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private CustomerRepo customerRepo;
    private TransactionRepo transactionRepo;


    public Customer findByUsername(String name){
        return customerRepo.findByUsername(name)
                .orElseThrow(() -> new AllUserTypeException("Customer wallet not found"));
    }


    public Customer registerCustomer(CustomerRegisterDto customerRegisterDto) {
        if(customerRepo.findByUsername(customerRegisterDto.getUsername()).isPresent()){
            throw new AllUserTypeException("Customer wallet already exist");
        };

        Customer customer = new Customer();
        customer.setUsername(customerRegisterDto.getUsername());
        customer.setPassword(passwordEncoder.encode(customerRegisterDto.getPassword()));
        return customerRepo.save(customer);
    }

    public void deposit(Customer customer, BigDecimal amount){
        customer.setBalance(customer.getBalance().add(amount));
        customerRepo.save(customer);

        Transaction transaction = new Transaction(amount, "Deposit", TransactionStatus.SUCCESS, LocalDateTime.now(), customer);
        transactionRepo.save(transaction);
    }

    public void withdraw(Customer customer, BigDecimal amount){
        if(customer.getBalance().compareTo(amount) < 0){
            throw new AllUserTypeException("Insufficient balance");
        }

        customer.setBalance(customer.getBalance().subtract(amount));
        customerRepo.save(customer);
        Transaction transaction = new Transaction(amount, "Withdrawal", TransactionStatus.SUCCESS, LocalDateTime.now(), customer);
        transactionRepo.save(transaction);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
//
//    public List<Transaction> getTransactions(CustomerWallet customerWallet){
//        return transactionRepo.findByWalletId(customerWallet.getId());
//    }
}
