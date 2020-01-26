package com.ant.backendservices.payload.request.display;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterDisplayRequest {

    @NotBlank
    @Size(min = 6, max = 6)
    private String pairCode;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

}
