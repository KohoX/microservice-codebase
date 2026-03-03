package com.kohobank.accountservice.mapper;

import com.kohobank.accountservice.dto.AccountDto;
import com.kohobank.accountservice.entity.Account;

public class AccountMapper {

    public static AccountDto mapAccountToAccountDto(Account account, AccountDto accountDto){
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBranchAddress(account.getBranchAddress());
        return accountDto;
    }

    public static Account mapAccountDtoToAccount(AccountDto accountDto, Account account){
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(account.getAccountType());
        account.setBranchAddress(account.getBranchAddress());
        return account;
    }
}
