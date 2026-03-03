package com.kohobank.loanservice.service;

import com.kohobank.loanservice.dto.LoanDto;

public interface LoanService {

    void createLoan(String mobileNumber);

    LoanDto getLoanDetails(String mobileNumber);

    boolean updateLoan(LoanDto loansDto);

    boolean deleteLoan(String mobileNumber);
}
