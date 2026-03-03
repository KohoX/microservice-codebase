package com.kohobank.cardservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        example = """
    {
      "apiPath": "/api/accounts/123",
      "errorCode": "BAD_REQUEST",
      "errorMessage": "Invalid account id",
      "errorTime": "2026-01-28T08:19:29.934Z"
    }
    """
)
public class ErrorResponseDto {

    @Schema(description = "API path invoked by client")
    private String apiPath;

    @Schema(description = "Error code")
    private HttpStatus errorCode;

    @Schema(description = "Error message")
    private String errorMessage;

    @Schema(description = "Time of occurred error")
    private LocalDateTime errorTime;

}
