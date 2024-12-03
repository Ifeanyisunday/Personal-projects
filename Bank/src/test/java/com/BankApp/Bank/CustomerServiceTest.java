package com.BankApp.Bank;


import com.BankApp.Bank.Repository.CustomerRepo;
import com.BankApp.Bank.Repository.TransactionRepo;
import com.BankApp.Bank.models.Customer;
import com.BankApp.Bank.services.implementations.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class CustomerServiceTest {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    CustomerServiceImpl customerService;

    @Test
    public void testCustomerCanDeposit() {
        Optional<Customer> customer = customerRepo.findByUsername("ify");
        Customer customer1 = customer.get();
        BigDecimal amount = new BigDecimal(200);
        customerService.deposit(customer1, amount);
        assertEquals(2, transactionRepo.count());
    }

    @Test
    public void testCustomerCanWithdraw() {
        Optional<Customer> customer = customerRepo.findByUsername("ify");
        Customer customer1 = customer.get();
        BigDecimal amount = new BigDecimal(500);
        customerService.withdraw(customer1, amount);
        assertEquals(1, transactionRepo.count());
    }
}
