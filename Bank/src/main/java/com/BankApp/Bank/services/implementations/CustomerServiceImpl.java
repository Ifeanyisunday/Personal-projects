package com.BankApp.Bank.services.implementations;

import com.BankApp.Bank.Repository.CustomerRepo;
import com.BankApp.Bank.Repository.TransactionRepo;
import com.BankApp.Bank.dtos.requests.CustomerRegisterDto;
import com.BankApp.Bank.exceptions.AllUserTypeException;
import com.BankApp.Bank.models.Customer;
import com.BankApp.Bank.models.Transaction;
import com.BankApp.Bank.models.TransactionStatus;
import io.jsonwebtoken.lang.Arrays;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements UserDetailsService {

    PasswordEncoder passwordEncoder;

    private CustomerRepo customerRepo;
    private TransactionRepo transactionRepo;


    public Customer findByUsername(String name){
        return customerRepo.findByUsername(name)
                .orElseThrow(() -> new AllUserTypeException("Customer not found"));
    }

    public Customer findCustomerByAcctNo(String acctNo){
        return customerRepo.findByAcctNo(acctNo)
                .orElseThrow(() -> new AllUserTypeException("Receiver account not found"));
    }

    private String generateAcctNo(){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder createAcctNo = new StringBuilder();

        for(int i = 0; i < 5; i++){
            secureRandom.nextInt(10);
            createAcctNo.append(secureRandom);
        }
        return createAcctNo.toString();
    }


    public String registerCustomer(CustomerRegisterDto customerRegisterDto) {
        if(customerRepo.findByUsername(customerRegisterDto.getUsername()).isPresent()){
            throw new AllUserTypeException("Customer wallet already exist");
        };

        Customer customer = new Customer();
        customer.setUsername(customerRegisterDto.getUsername());
        customer.setPassword(passwordEncoder.encode(customerRegisterDto.getPassword()));
        customer.setBalance(BigDecimal.ZERO);
        customer.setAcctNo(generateAcctNo());
        customerRepo.save(customer);
        return "Registration successfull";
    }

    public String deposit(Customer customer, BigDecimal amount){
        customer.setBalance(customer.getBalance().add(amount));
        customerRepo.save(customer);

        Transaction transaction = new Transaction(amount, "Deposit", TransactionStatus.SUCCESS, LocalDateTime.now(), customer);
        transactionRepo.save(transaction);
        return "Success";
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

    public List<Transaction> transactionHistory(Customer customer){
        return transactionRepo.findByCustomerId(customer.getId());
    }

    public void transferFunds(Customer customer, BigDecimal amount, String recipientAcctNo){
        if(customer.getBalance().compareTo(amount) < 0){
            throw new AllUserTypeException("Insufficient balance");
        }

        customer.setBalance(customer.getBalance().subtract(amount));
        customerRepo.save(customer);
        Customer receiver = findCustomerByAcctNo(recipientAcctNo);
        receiver.setBalance(receiver.getBalance().add(amount));
        customerRepo.save(receiver);

        Transaction senderTransferRecord = new Transaction(amount,
                "Transfer to" + receiver.getUsername(),
                TransactionStatus.SUCCESS, LocalDateTime.now(), customer);
        transactionRepo.save(senderTransferRecord);

        Transaction receiverTransferRecord = new Transaction(amount,
                "Received " + amount + "from " + customer.getUsername(),
                TransactionStatus.SUCCESS, LocalDateTime.now(), receiver);
        transactionRepo.save(receiverTransferRecord);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = findByUsername(username);

        if(customer == null){
            throw new UsernameNotFoundException("Customer with name or password not found");
        }
        return new Customer(customer.getUsername(),
                customer.getPassword(),
                customer.getAcctNo(),
                customer.getBalance(),
                customer.getTransactions(),
                authorities()
        );
    }

    public Collection<? extends GrantedAuthority> authorities(){
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("User"));
//         Arrays.asList(new SimpleGrantedAuthority("User"));
        return authorities;
    }
//
//    public List<Transaction> getTransactions(CustomerWallet customerWallet){
//        return transactionRepo.findByWalletId(customerWallet.getId());
//    }
}
