package com.kohobank.cardservice.controller;

import com.kohobank.cardservice.constants.ResponseConstant;
import com.kohobank.cardservice.dto.CardContactInfoDto;
import com.kohobank.cardservice.dto.CardDto;
import com.kohobank.cardservice.dto.ErrorResponseDto;
import com.kohobank.cardservice.dto.ResponseDto;
import com.kohobank.cardservice.service.CardService;
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
        description = "CRUD REST APIs to perform CRUD Operation for Card service")
@RestController
@RequestMapping(path = "/api/v1/card", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    private final CardService cardService;

    @Autowired
    private CardContactInfoDto cardContactInfoDto;

    @Operation(summary = "Create Card",description = "API to create new Card ")
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
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam
                                                      @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                      String mobileNumber) {
        cardService.createCard(mobileNumber);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ResponseConstant.created());
    }

    @Operation(summary = "Get Card details",description = "API to Card details of the customer ")
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
    public ResponseEntity<CardDto> getCardDetails( @RequestHeader("correlation-id") String correlationId,
            @RequestParam @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                               String mobileNumber) {
        logger.debug("correlation-id : {}",correlationId);
        CardDto cardDto = cardService.getCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardDto);
    }

    @Operation(summary = "Update Card details",description = "API to update loan details for a customer")
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
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardDto cardDto) {
        boolean isUpdated = cardService.updateCard(cardDto);
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

    @Operation(summary = "Delete Card details",description = "API to card details for a customer ")
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
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam
                                                                @Pattern(regexp="(^[0-9]{10}$)",message = "Mobile number must be 10 digits")
                                                                String mobileNumber) {
        boolean isDeleted = cardService.deleteCard(mobileNumber);
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
    public ResponseEntity<CardContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardContactInfoDto);
    }

}
