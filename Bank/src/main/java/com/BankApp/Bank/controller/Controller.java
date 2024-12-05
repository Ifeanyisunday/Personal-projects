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

//    @PostMapping("/transfer")
//    public ResponseEntity<?> transfer(@RequestParam BigDecimal amount, String username) {
////        Customer customer = customerService.findByUsername(authentication.getName());
//        try {
//            customerService.transferFunds(amount, username);
//            return ResponseEntity.ok("Transfer successful. New balance: " + customer.getBalance());
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }


//    @PostMapping("/transactions")
//    public List<Transaction> transactions() {
////        Customer customer = customerService.findByUsername(authentication.getName());
//        return customerService.transactionHistory();
//    }


//    @GetMapping("/login")
//    public ResponseEntity<?> login(@RequestBody CustomerRegisterDto customerRegisterDto, Authentication authentication) {
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
//        }
//
//        return ResponseEntity.ok("Login successful. Welcome " + authentication.getName());
//    }
//
//    @GetMapping("/me")
//    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return ResponseEntity.status(401).body("Not authenticated");
//        }
//        return ResponseEntity.ok(authentication.getName());
//    }
//
//    @GetMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
//        SecurityContextHolder.clearContext();
//        request.getSession().invalidate();
//
//        response.setStatus(HttpServletResponse.SC_OK);
//        return ResponseEntity.ok("Logout successful");
//    }
}

//@org.springframework.stereotype.Controller
//@AllArgsConstructor
//public class Controller {
//
//    private CustomerServiceImpl customerService;
//
//    @GetMapping("/dashboard")
//    public String dashboard(Model model){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Customer customer =  customerService.findByUsername(username);
//        model.addAttribute("customer", customer);
//        return "dashboard";
//    }
//
//    @GetMapping("/register")
//    public String registerForm(){
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerCustomer(@RequestBody CustomerRegisterDto customerRegisterDto){
//        try {
//            customerService.registerCustomer(customerRegisterDto);
//            return "redirect:/dashboard";
//        }catch (RuntimeException e){
//            return "register";
//        }
//    }
//
//    @GetMapping("/login")
//    public String login(){
//        return "dashboard";
//    }
//
//    @PostMapping("/deposit")
//    public String deposit(@RequestParam BigDecimal amount){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Customer customer =  customerService.findByUsername(username);
//        customerService.deposit(customer, amount);
//        return "redirect:/dashboard";
//    }
//
//    @PostMapping("/withdraw")
//    public String withdraw(@RequestParam BigDecimal amount, Model model){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Customer customer =  customerService.findByUsername(username);
//
//        try{
//            customerService.withdraw(customer, amount);
//        }catch (RuntimeException e){
//            model.addAttribute("error", e.getMessage());
//            model.addAttribute("customer", customer);
//            return "dashboard";
//        }
//
//        return "redirect:/dashboard";
//    }
//
//    @GetMapping("/transactions")
//    public String transactions(Model model){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Customer customer =  customerService.findByUsername(username);
//
//        model.addAttribute("transactions", customerService.transactionHistory(customer));
//        return "transactions";
//    }
//
//    @GetMapping("/transfer")
//    public String transfer(@RequestParam BigDecimal amount, @RequestParam String acctNo, Model model){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Customer customer =  customerService.findByUsername(username);
//
//        try{
//            customerService.transferFunds(customer, amount, acctNo);
//        }catch (RuntimeException e){
//            model.addAttribute("error", e.getMessage());
//            model.addAttribute("customer", customer);
//            return "dashboard";
//        }
//
//        return "redirect:/dashboard";
//    }
//
//
//
//}
