package com.BankApp.Bank.services.implementations;

import com.BankApp.Bank.Repository.CustomerRepo;
import com.BankApp.Bank.Repository.TransactionRepo;
import com.BankApp.Bank.dtos.requests.CustomerDepositRequest;
import com.BankApp.Bank.dtos.requests.CustomerRegisterDto;
import com.BankApp.Bank.dtos.requests.TransferDto;
import com.BankApp.Bank.exceptions.AllUserTypeException;
import com.BankApp.Bank.models.Customer;
import com.BankApp.Bank.models.Transaction;
import com.BankApp.Bank.models.TransactionStatus;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class CustomerServiceImpl{

//    PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    private String username;


    public Customer findByUsername(String name){
        return customerRepo.findByUsername(name)
                .orElseThrow(() -> new AllUserTypeException("Customer not found or enter login credentials"));
    }

    public Customer findCustomerByAcctNo(String acctNo){
        return customerRepo.findByAcctNo(acctNo)
                .orElseThrow(() -> new AllUserTypeException("Receiver account not found"));
    }

    private String generateAcctNo(){
        Random secureRandom = new Random();
        StringBuilder createAcctNo = new StringBuilder();

        for(int i = 0; i < 5; i++){
            secureRandom.nextInt(10);
            createAcctNo.append(secureRandom);
        }
        return createAcctNo.toString();
    }


    public Customer registerCustomer(CustomerRegisterDto customerRegisterDto) {
        if(customerRepo.findByUsername(customerRegisterDto.getUsername()).isPresent()){
            throw new AllUserTypeException("Customer wallet already exist");
        };

        Customer customer = new Customer();
        customer.setUsername(customerRegisterDto.getUsername());
        customer.setPassword(customerRegisterDto.getPassword());
        customer.setBalance(BigDecimal.ZERO);
        customer.setAcctNo(generateAcctNo());
        login(customerRegisterDto);
        return customerRepo.save(customer);
    }

    public void deposit(CustomerDepositRequest customerDepositRequest){
        Customer customer = findByUsername(getUsername());
        customer.setBalance(customer.getBalance().add(customerDepositRequest.getAmount()));
        customerRepo.save(customer);

        Transaction transaction = new Transaction(customerDepositRequest.getAmount(), "Deposit", TransactionStatus.SUCCESS, LocalDateTime.now(), customer);
        transactionRepo.save(transaction);
    }

    public void withdraw(CustomerDepositRequest customerDepositRequest){
        Customer customer = findByUsername(getUsername());
        if(customer.getBalance().compareTo(customerDepositRequest.getAmount()) < 0){
            throw new AllUserTypeException("Insufficient balance");
        }

        customer.setBalance(customer.getBalance().subtract(customerDepositRequest.getAmount()));
        customerRepo.save(customer);
        Transaction transaction = new Transaction(customerDepositRequest.getAmount(), "Withdrawal", TransactionStatus.SUCCESS, LocalDateTime.now(), customer);
        transactionRepo.save(transaction);
    }

    public List<Transaction> transactionHistory(){
        Customer customer = findByUsername(getUsername());
        return transactionRepo.findByCustomerId(customer.getId());
    }

    public void transferFunds(TransferDto transferDto){
        Customer customer = findByUsername(getUsername());
        if(customer.getBalance().compareTo(transferDto.getAmount()) < 0){
            throw new AllUserTypeException("Insufficient balance");
        }

        customer.setBalance(customer.getBalance().subtract(transferDto.getAmount()));
        customerRepo.save(customer);
        Customer receiver = findByUsername(transferDto.getReceiver());
        receiver.setBalance(receiver.getBalance().add(transferDto.getAmount()));
        customerRepo.save(receiver);

        Transaction senderTransferRecord = new Transaction(transferDto.getAmount(),
                "Transfer to" + receiver.getUsername(),
                TransactionStatus.SUCCESS, LocalDateTime.now(), customer);
        transactionRepo.save(senderTransferRecord);

        Transaction receiverTransferRecord = new Transaction(transferDto.getAmount(),
                "Received " + transferDto.getAmount() + "from " + customer.getUsername(),
                TransactionStatus.SUCCESS, LocalDateTime.now(), receiver);
        transactionRepo.save(receiverTransferRecord);
    }

    public void login(CustomerRegisterDto customerRegisterDto){
//        if(this.username.length() > 0){
//            throw new AllUserTypeException("Try logging out from this user");
//        }
        this.username = customerRegisterDto.getUsername();
    }

    public void logout(){
        this.username = "";
    }

    public String getUsername() {
        return username;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Customer customer = findByUsername(username);
//
//        if(customer == null){
//            throw new UsernameNotFoundException("Customer with name or password not found");
//        }
//        return new Customer(customer.getUsername(),
//                customer.getPassword(),
//                customer.getAcctNo(),
//                customer.getBalance(),
//                customer.getTransactions(),
//                authorities()
//        );
//    }


//
//    public List<Transaction> getTransactions(CustomerWallet customerWallet){
//        return transactionRepo.findByWalletId(customerWallet.getId());
//    }
}
