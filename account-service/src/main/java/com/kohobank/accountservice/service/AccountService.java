package com.kohobank.accountservice.service;

import com.kohobank.accountservice.dto.CustomerDto;

import java.util.List;

public interface AccountService{
    void createAccount(CustomerDto customerDto);

    CustomerDto getCustomer(String mobileNumber);

    boolean updateAccountDetails(CustomerDto customerDto);

    boolean deleteAccountDetails(Long accountNumber);

    List<CustomerDto> getAllCustomers();

}
