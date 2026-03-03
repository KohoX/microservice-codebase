package com.kohobank.loanservice.controller;

import com.kohobank.loanservice.constants.ResponseConstant;
import com.kohobank.loanservice.dto.ErrorResponseDto;
import com.kohobank.loanservice.dto.LoanContactInfoDto;
import com.kohobank.loanservice.dto.LoanDto;
import com.kohobank.loanservice.dto.ResponseDto;
import com.kohobank.loanservice.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "CRUD REST APIs for loan-service",
        description = "CRUD REST APIs to perform CRUD Operation for loan service")
@RestController
@RequestMapping(path = "/api/v1/loan", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    private final LoanService loanService;

    @Autowired
    private LoanContactInfoDto loanContactInfoDto;

    @Operation(summary = "Create Loan",description = "API to create new loan ")
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
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                      @Pattern(regexp="(^[0-9]{10}$)",message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseConstant.created());
    }

    @Operation(summary = "Get Loan Details",description = "API to get loan details for a customer")
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
    @GetMapping
    public ResponseEntity<LoanDto> getLoanDetails(@RequestHeader("correlation-id") String correlationId,
            @RequestParam @Pattern(regexp="(^[0-9]{10}$)",message = "Mobile number must be 10 digits")
                                                               String mobileNumber) {
        logger.debug("correlation-Id: {}",correlationId);
        LoanDto loanDto = loanService.getLoanDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loanDto);
    }

    @Operation(summary = "Update loan details",description = "API to update loan details for a customer")
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
    public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoanDto loanDto) {
        boolean isUpdated = loanService.updateLoan(loanDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseConstant.success());
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ResponseConstant.expectationFailed());
        }
    }

    @Operation(summary = "Delete loan details",description = "API to delete loan details for a customer")
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
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam
                                                                @Pattern(regexp="(^[0-9]{10}$)",message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        boolean isDeleted = loanService.deleteLoan(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseConstant.success());
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ResponseConstant.expectationFailed());
        }
    }

    @GetMapping("/contact-info")
    public ResponseEntity<LoanContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanContactInfoDto);
    }

}
