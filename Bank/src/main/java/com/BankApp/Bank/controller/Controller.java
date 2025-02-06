package com.BankApp.Bank.controller;

import com.BankApp.Bank.dtos.requests.CustomerDepositRequest;
import com.BankApp.Bank.dtos.requests.CustomerRegisterDto;
import com.BankApp.Bank.dtos.requests.TransferDto;
import com.BankApp.Bank.models.Customer;
import com.BankApp.Bank.models.Transaction;
import com.BankApp.Bank.services.implementations.CustomerServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class Controller {

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CustomerRegisterDto customerRegisterDto) {
        try {
            customerService.registerCustomer(customerRegisterDto);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody CustomerDepositRequest customerDepositRequest) {
//        Customer customer = customerService.findByUsername(authentication.getName());
        try {
            customerService.deposit(customerDepositRequest);
            return ResponseEntity.ok(Map.of("message", "Deposit successfull"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody CustomerDepositRequest customerDepositRequest) {
//        Customer customer = customerService.findByUsername(authentication.getName());
        try {
            customerService.withdraw(customerDepositRequest);
            return ResponseEntity.ok(Map.of("message", "Withdraw successfull"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerRegisterDto customerRegisterDto){
        try {
            customerService.login(customerRegisterDto);
            return ResponseEntity.ok(Map.of("message", "You are logged in"));
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        try {
            customerService.logout();
            return ResponseEntity.ok(Map.of("message","You are logged out"));
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto) {
//        Customer customer = customerService.findByUsername(authentication.getName());
        try {
            customerService.transferFunds(transferDto);
            return ResponseEntity.ok(Map.of("message","Transfer successful."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> transactions() {
//        Customer customer = customerService.findByUsername(authentication.getName());
        try {
            List<Transaction> transaction = customerService.transactionHistory();
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
