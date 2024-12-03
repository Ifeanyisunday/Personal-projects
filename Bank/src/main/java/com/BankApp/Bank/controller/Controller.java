package com.BankApp.Bank.controller;

import com.BankApp.Bank.dtos.requests.CustomerRegisterDto;
import com.BankApp.Bank.dtos.response.ApiResponse;
import com.BankApp.Bank.models.Customer;
import com.BankApp.Bank.services.implementations.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
public class Controller {

    private CustomerServiceImpl customerService;

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer =  customerService.findByUsername(username);
        model.addAttribute("customer", customer);
        return "dashboard";
    }

    @PostMapping("/registerCustomer")
    public ResponseEntity<?> registration(@RequestBody CustomerRegisterDto customerRegisterDto) {
        try {
            var result = customerService.registerCustomer(customerRegisterDto);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam BigDecimal amount) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Customer customer =  customerService.findByUsername(username);
            var result = customerService.deposit(customer, amount);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam BigDecimal amount) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Customer customer =  customerService.findByUsername(username);
            var result = customerService.withdraw(customer, amount);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

}
