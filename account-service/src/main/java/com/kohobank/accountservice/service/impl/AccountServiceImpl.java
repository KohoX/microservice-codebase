package com.kohobank.accountservice.service.impl;

import com.kohobank.accountservice.constants.AccountConstants;
import com.kohobank.accountservice.dto.AccountDto;
import com.kohobank.accountservice.dto.CustomerDto;
import com.kohobank.accountservice.entity.Account;
import com.kohobank.accountservice.entity.Customer;
import com.kohobank.accountservice.exception.CustomerAlreadyExistException;
import com.kohobank.accountservice.exception.ResourceNotFoundException;
import com.kohobank.accountservice.mapper.AccountMapper;
import com.kohobank.accountservice.mapper.CustomerMapper;
import com.kohobank.accountservice.repository.AccountRepository;
import com.kohobank.accountservice.repository.CustomerRepository;
import com.kohobank.accountservice.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    /**
     * Service Method to create account for the customer.
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistException("Customer already registered with given mobileNumber : "
                    +customerDto.getMobileNumber());
        }

        Customer customer = CustomerMapper.mapCustomerDtoToCustomer(customerDto, new Customer());
//        customer.setCreatedAt(LocalDateTime.now());
//        customer.setCreatedBy(AccountConstants.ANONYMOUS);
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto getCustomer(String mobileNumber) {
        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );

        Account account = accountRepository.findAccountByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","customerId",String.valueOf(customer.getCustomerId()))
        );
        CustomerDto customerDto = CustomerMapper.mapCustomerToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountDetails(AccountMapper.mapAccountToAccountDto(account,new AccountDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccountDetails(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDetails();
        if(accountDto != null){
            Account account = accountRepository.findAccountByAccountNumber(accountDto.getAccountNumber()).orElseThrow(
                    ()-> new ResourceNotFoundException("Account","AccountNumber",String.valueOf(accountDto.getAccountNumber()))
            );
            AccountMapper.mapAccountDtoToAccount(accountDto,account);
            account = accountRepository.save(account);

            Long customerId = account.getCustomerId();

            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer","customerId",String.valueOf(customerId))
            );
            CustomerMapper.mapCustomerDtoToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccountDetails(Long accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(
                () -> new ResourceNotFoundException("Account","accountNumber",String.valueOf(accountNumber))
        );
        Long customerId = account.getCustomerId();
        customerRepository.deleteCustomerByCustomerId(customerId);
        accountRepository.deleteById(accountNumber);
        return true;
    }

    // Optimized query
    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        if(customers.isEmpty()){
            return Collections.emptyList();
        }

        // Extract customerIds
        List<Long> customerIds = customers.stream().map(Customer::getCustomerId).toList();

        /**
         * List<Long> customerIds = [101L, 102L, 103L];
         */


        // Fetch all accounts in one query
        List<Account> accounts = accountRepository.findAllByCustomerIdIn(customerIds);


        /** Fecth all account from above query
         * List<Account> accounts = [
         *     Account(accountNumber=1001, customerId=101, accountType="SAVINGS", branchAddress="Branch A"),
         *     Account(accountNumber=1002, customerId=102, accountType="CURRENT", branchAddress="Branch B"),
         *     Account(accountNumber=1003, customerId=103, accountType="SALARY", branchAddress="Branch C")
         *
         */

        Map<Long,Account> accountMap = new HashMap<>();
        for(Account account : accounts){
            accountMap.put(account.getCustomerId(),account);
        }

        /** After Map operation
         * {
         *     101L → Account(accountNumber=1004, customerId=101, accountType="LOAN", branchAddress="Branch D"),
         *     102L → Account(accountNumber=1002, customerId=102, accountType="CURRENT", branchAddress="Branch B"),
         *     103L → Account(accountNumber=1003, customerId=103, accountType="SALARY", branchAddress="Branch C")
         * }
         *
         */

        List<CustomerDto> customerDtos = new ArrayList<>();
        for(Customer customer : customers){
            Account account = accountMap.get(customer.getCustomerId());
            if(account == null){
                log.info("No account for customer : {}", customer.getCustomerId());
                continue;
            }
            CustomerDto customerDto = CustomerMapper.mapCustomerToCustomerDto(customer, new CustomerDto());
            AccountDto accountDto = AccountMapper.mapAccountToAccountDto(account,new AccountDto());
            customerDto.setAccountDetails(accountDto);
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }

   /* @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        *//*List<CustomerDto> customerDtos = customers.stream().map(customer -> {
            CustomerDto customerDto = CustomerMapper.mapCustomerToCustomerDto(customer, new CustomerDto());
            accountRepository.findAccountByCustomerId(customer.getCustomerId())
                    .ifPresent(account -> {
                        AccountDto accountDto = AccountMapper.mapAccountToAccountDto(account,new AccountDto());
                        customerDto.setAccountDetails(accountDto);
                    });
            return customerDto;
        }).collect(Collectors.toList());*//*
        List<CustomerDto> customerDtos = new ArrayList<>();
        for(Customer customer : customers){
            Optional<Account> account = accountRepository.findAccountByCustomerId(customer.getCustomerId());
            if(account.isEmpty()){
                log.info("No account for customer : {}", customer.getCustomerId());
                continue;
            }
            CustomerDto customerDto = CustomerMapper.mapCustomerToCustomerDto(customer, new CustomerDto());
            AccountDto accountDto = AccountMapper.mapAccountToAccountDto(account.get(),new AccountDto());
            customerDto.setAccountDetails(accountDto);
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }*/

    //


    private Account createNewAccount(Customer customer){
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());

        Long randomAccNumber = 1000000000L + new SecureRandom().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);

//        newAccount.setCreatedAt(LocalDateTime.now());
//        newAccount.setCreatedBy(AccountConstants.ANONYMOUS);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        return newAccount;
    }
}
