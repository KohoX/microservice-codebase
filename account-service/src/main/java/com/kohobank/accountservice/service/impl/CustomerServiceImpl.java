package com.kohobank.accountservice.service.impl;

import com.kohobank.accountservice.dto.AccountDto;
import com.kohobank.accountservice.dto.CardDto;
import com.kohobank.accountservice.dto.CustomerDetailsDto;
import com.kohobank.accountservice.dto.LoanDto;
import com.kohobank.accountservice.entity.Account;
import com.kohobank.accountservice.entity.Customer;
import com.kohobank.accountservice.exception.ResourceNotFoundException;
import com.kohobank.accountservice.mapper.AccountMapper;
import com.kohobank.accountservice.mapper.CustomerMapper;
import com.kohobank.accountservice.repository.AccountRepository;
import com.kohobank.accountservice.repository.CustomerRepository;
import com.kohobank.accountservice.service.CustomerService;
import com.kohobank.accountservice.service.client.CardFeignClient;
import com.kohobank.accountservice.service.client.LoanFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final LoanFeignClient loanFeignClient;
    private final CardFeignClient cardFeignClient;


    /**
     * Service method to get the customer details
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDetailsDto getCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Account account = accountRepository.findAccountByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapCustomerToCustomerDetailsDto(customer,new CustomerDetailsDto());
        customerDetailsDto.setAccountDetails(AccountMapper.mapAccountToAccountDto(account,new AccountDto()));

        ResponseEntity<CardDto> cardDtoResponse = cardFeignClient.getCardDetails(correlationId, mobileNumber) ;
        customerDetailsDto.setCardDetails(cardDtoResponse.getBody());

        ResponseEntity<LoanDto> loanDtoResponse = loanFeignClient.getLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoanDetails(loanDtoResponse.getBody());

        return customerDetailsDto;
    }
}
