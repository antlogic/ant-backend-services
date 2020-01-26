package com.ant.backendservices.payload.response;

import lombok.Data;

@Data
public class JwtPairResponse {
    private Boolean isRegistered;
    private String accessToken;
    private String tokenType = "Bearer";
}
