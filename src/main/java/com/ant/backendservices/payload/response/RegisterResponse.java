package com.ant.backendservices.payload.response;

import lombok.Data;

@Data
public class RegisterResponse {
    private Boolean success;
    private String message;

    public RegisterResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
