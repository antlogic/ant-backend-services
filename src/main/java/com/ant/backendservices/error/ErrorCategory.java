package com.ant.backendservices.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCategory {

    VALIDATION_ERROR("Invalid request.", HttpStatus.BAD_REQUEST),
    AUTHORIZATION_ERROR("Invalid credentials.", HttpStatus.UNAUTHORIZED),
    INTERNAL_SERVER_ERROR("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);

    private String message;
    private HttpStatus httpStatus;

    ErrorCategory(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
