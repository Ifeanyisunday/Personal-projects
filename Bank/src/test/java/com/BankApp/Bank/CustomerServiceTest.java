package com.BankApp.Bank;


import com.BankApp.Bank.Repository.CustomerRepo;
import com.BankApp.Bank.Repository.TransactionRepo;
import com.BankApp.Bank.dtos.requests.CustomerDepositRequest;
import com.BankApp.Bank.dtos.requests.CustomerRegisterDto;
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
    public void testCustomerCanRegister() {
        CustomerRegisterDto customerRegisterDto = new CustomerRegisterDto();
        customerRegisterDto.setUsername("ify");
        customerRegisterDto.setPassword("11111");
        customerService.registerCustomer(customerRegisterDto);
        assertEquals(1, customerRepo.count());
    }

    @Test
    public void testCustomerCanDeposit() {
//        Optional<Customer> customer = customerRepo.findByUsername("ify");
//        Customer customer1 = customer.get();
        CustomerRegisterDto customerRegisterDto = new CustomerRegisterDto();
        customerRegisterDto.setUsername("ify");
        customerRegisterDto.setPassword("11111");
        customerService.login(customerRegisterDto);
        CustomerDepositRequest customerDepositRequest = new CustomerDepositRequest();
        BigDecimal amount = new BigDecimal(200);
        customerDepositRequest.setAmount(amount);
        customerService.deposit(customerDepositRequest);
        assertEquals(1, transactionRepo.count());
    }

    @Test
    public void testCustomerCanWithdraw() {
        Optional<Customer> customer = customerRepo.findByUsername("ify");
        Customer customer1 = customer.get();
        CustomerDepositRequest customerDepositRequest = new CustomerDepositRequest();
        BigDecimal amount = new BigDecimal(500);
        customerDepositRequest.setAmount(amount);
        customerService.withdraw(customerDepositRequest);
        assertEquals(1, transactionRepo.count());
    }
}
