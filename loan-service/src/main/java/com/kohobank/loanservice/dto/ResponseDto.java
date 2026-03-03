package com.kohobank.loanservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "Response", description = "Schema to hold successful response information")
public class ResponseDto {

    @Schema(description = "status code")
    private String statusCode;

    @Schema(description = "status message")
    private String statusMessage;
}
