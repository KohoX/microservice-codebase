package com.kohobank.accountservice.service.client;

import com.kohobank.accountservice.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loan-service")
public interface LoanFeignClient {

    @GetMapping(value = "/api/v1/loan",consumes = "application/json")
    public ResponseEntity<LoanDto> getLoanDetails(@RequestHeader("correlation-id") String correlationId,
                                                  @RequestParam String mobileNumber);
}
