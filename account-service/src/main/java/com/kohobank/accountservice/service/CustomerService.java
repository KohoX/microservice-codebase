package com.kohobank.accountservice.service;

import com.kohobank.accountservice.dto.CustomerDetailsDto;

public interface CustomerService {

    CustomerDetailsDto getCustomerDetails(String mobileNumber, String correlationId);
}
