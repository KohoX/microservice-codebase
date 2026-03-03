package com.kohobank.loanservice.constants;

public final class ResponseCodesAndMessages {

    private ResponseCodesAndMessages(){}

    // Status Codes
    public static final String SUCCESS_CODE = "200";
    public static final String CREATED_CODE = "201";
    public static final String BAD_REQUEST_CODE = "400";
    public static final String EXPECTATION_FAILED = "417";
    public static final String UNAUTHORIZED_CODE = "401";
    public static final String FORBIDDEN_CODE = "403";
    public static final String NOT_FOUND_CODE = "404";
    public static final String CONFLICT_CODE = "409";
    public static final String INTERNAL_SERVER_ERROR_CODE = "500";

    // Status Message
    public static final String SUCCESS_MESSAGE = "Operation completed successfully";
    public static final String CREATED_MESSAGE = "Resource created successfully";
    public static final String BAD_REQUEST_MESSAGE = "Invalid request parameters";
    public static final String EXPECTATION_FAILED_MESSAGE = "Operation failed. Please try again or contact Dev team";
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized access";
    public static final String FORBIDDEN_MESSAGE = "Access is forbidden";
    public static final String NOT_FOUND_MESSAGE = "Resource not found";
    public static final String CONFLICT_MESSAGE = "Resource conflict occurred";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error occurred";
}
