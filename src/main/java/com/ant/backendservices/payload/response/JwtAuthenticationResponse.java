package com.ant.backendservices.payload.response;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long accessTokenExpiry;
    private String firstName;
    private String lastName;
}
