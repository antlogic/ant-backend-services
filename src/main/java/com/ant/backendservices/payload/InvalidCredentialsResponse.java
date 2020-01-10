package com.ant.backendservices.payload;

import lombok.Data;

@Data
public class InvalidCredentialsResponse {
    private String error = "INVALID_CREDENTIALS";
    private String message;
}
