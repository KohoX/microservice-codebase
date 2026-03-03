package com.kohobank.accountservice.controller;

import com.kohobank.accountservice.dto.CustomerDetailsDto;
import com.kohobank.accountservice.dto.ErrorResponseDto;
import com.kohobank.accountservice.dto.ResponseDto;
import com.kohobank.accountservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "REST APIs for Customers in KOHO Bank",
        description = "REST APIs to fetch customer details")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/customer", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    /**
     * Controller method to get the customer details.
     * @param mobileNumber
     * @return
     */
    @Operation(summary = "Get customer details",description = "API to fetch customer details based on mobileNumber ")
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Ok",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "201",description = "Created",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "401",description = "Unauthorized",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "403",description = "Forbidden",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404",description = "Not Found",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500",description = "Internal Server Error",content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(@RequestHeader("correlation-id") String correlationId,
            @PathVariable @Pattern(regexp="(^[0-9]{10}$)",message = "Mobile number must be 10 digits")
                                                                     String mobileNumber){

        logger.debug("correlation-Id: {}",correlationId);
        CustomerDetailsDto customerDetailsDto = customerService.getCustomerDetails(mobileNumber,correlationId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDetailsDto);
    }
}
