package com.kohobank.accountservice.service.client;

import com.kohobank.accountservice.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("card-service")
public interface CardFeignClient {

    @GetMapping(value = "/api/v1/card",consumes = "application/json")
    public ResponseEntity<CardDto> getCardDetails(@RequestHeader("correlation-id") String correlationId,
                                                  @RequestParam String mobileNumber);
}
