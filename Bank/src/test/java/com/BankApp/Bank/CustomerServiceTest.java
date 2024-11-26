package com.BankApp.Bank;

import com.BankApp.Bank.Repository.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class CustomerServiceTest {

    ArrayList<Customer> customerList = new ArrayList<>();

    CustomerRepo customerRepo;


    @Test
    void testThatCustormerCanRegister(){
        
    }
}
