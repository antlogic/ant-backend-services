package com.ant.backendservices.payload.request.display;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterDisplayRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String orientation;

    private Long transitionTimeMillis;

    @NotNull
    private Long locationId;
}
