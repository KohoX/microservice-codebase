package com.kohobank.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonPropertyOrder({"name","email","mobileNumber","accountDetails","cardDetails","loanDetails"})
@Schema(name = "CustomerDetails", description = "Schema to hold the Customer, Account, Card and Loan information")
public class CustomerDetailsDto {

    @JsonProperty("name")
    @NotEmpty(message = "Name cannot be null or an empty")
    @Size(min = 5,max = 30, message = "The length of the customer name should be between 5 and 30")
    @Schema(description = "Name of the customer")
    private String name;

    @JsonProperty("email")
    @NotEmpty(message = "Email address cannot be null or empty")
    @Email(message = "Email address should be a valid value")
    @Schema(description = "Email address of the customer",example = "djoe@email.com")
    private String email;

    @JsonProperty("mobileNumber")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    @Schema(description = "Mobile number of the customer")
    private String mobileNumber;

    @JsonProperty("accountDetails")
    @Schema(description = "Account details of the Customer")
    private AccountDto accountDetails;

    @JsonProperty("cardDetails")
    @Schema(description = "Card details of the Customer")
    private CardDto cardDetails;

    @JsonProperty("loanDetails")
    @Schema(description = "Loan details of the Customer")
    private LoanDto loanDetails;
}
