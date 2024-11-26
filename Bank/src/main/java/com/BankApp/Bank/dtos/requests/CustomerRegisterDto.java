package com.BankApp.Bank.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRegisterDto {
    private String name;
    private String phoneNo;
    private String password;
}
