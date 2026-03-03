package com.kohobank.accountservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Account",description = "Schema to hold the account information")
public class AccountDto {

    @JsonProperty("accountNumber")
    @NotEmpty(message = "AccountNumber can not be null or empty")
    @Pattern(regexp="(^[0-9]{10}$)",message = "AccountNumber must be 10 digits")
    @Schema(description = "Bank account number",example = "6953621478")
    private Long accountNumber;

    @JsonProperty("accountType")
    @NotEmpty(message = "AccountType can not be null or empty")
    @Schema(description = "Types of bank account: SAVING or CURRENT")
    private String accountType;

    @JsonProperty("branchAddress")
    @NotEmpty(message = "BranchAddress can not be null or empty")
    @Schema(description = "Address of the bank")
    private String branchAddress;

}
