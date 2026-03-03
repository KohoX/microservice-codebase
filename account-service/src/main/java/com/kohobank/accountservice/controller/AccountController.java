package com.kohobank.accountservice.controller;

import com.kohobank.accountservice.constants.ResponseConstant;
import com.kohobank.accountservice.dto.AccountContactInfoDto;
import com.kohobank.accountservice.dto.CustomerDto;
import com.kohobank.accountservice.dto.ErrorResponseDto;
import com.kohobank.accountservice.dto.ResponseDto;
import com.kohobank.accountservice.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Tag(name = "CRUD REST APIs for account-service",
        description = "CRUD REST APIs to perform CRUD Operation")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/account", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {

    private final AccountService accountService;

    @Autowired
    private AccountContactInfoDto accountContactInfoDto;


    @Operation(summary = "Create new account",description = "API to create new account for new customer")
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
    @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        accountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseConstant.created());
    }

    @Operation(summary = "Get account details",description = "API to fecth account details based on mobileNumber ")
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
    public ResponseEntity<Object> getCustomer(@PathVariable String mobileNumber){

        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        Matcher matcher = pattern.matcher(mobileNumber);
        if(!matcher.matches()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseConstant.badRequest());
        }

        CustomerDto customerDto = accountService.getCustomer(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDto);
    }

    @Operation(summary = "Get all account details for the customer",description = "API to fetch all the account details")
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
    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(){
        List<CustomerDto> customerList = accountService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerList);
    }

    @Operation(summary = "Update the account information",description = "API to update the account information of the customer")
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
    @PutMapping
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = accountService.updateAccountDetails(customerDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseConstant.success());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseConstant.internalServerError());
        }
    }

    @Operation(summary = "Delete the account details",description = "API to delete the account information of the customer")
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
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@PathVariable String accountNumber){

        Pattern pattern = Pattern.compile("^[0-9]{10}$");
        Matcher matcher = pattern.matcher(accountNumber);
        if(!matcher.matches()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseConstant.badRequest());
        }

        Long acctNumber = Long.parseLong(accountNumber);
        boolean isDeleted = accountService.deleteAccountDetails(acctNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseConstant.success());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseConstant.internalServerError());
        }
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountContactInfoDto);
    }
}
