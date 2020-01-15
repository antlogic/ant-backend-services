package com.ant.backendservices.payload.response;

import lombok.Data;

@Data
public class InvalidCredentialsResponse {
    private String error = "INVALID_CREDENTIALS";
    private String message;
}
