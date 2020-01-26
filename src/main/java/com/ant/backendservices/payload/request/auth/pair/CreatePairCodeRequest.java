package com.ant.backendservices.payload.request.auth.pair;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreatePairCodeRequest {
    @NotBlank
    private String model;

    @NotBlank
    private String manufacturer;

    @NotNull
    private Boolean isLandscape;
}
