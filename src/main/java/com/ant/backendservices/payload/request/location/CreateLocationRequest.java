package com.ant.backendservices.payload.request.location;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateLocationRequest {

    @NotBlank
    private String name;

    private String address;

    private Long phoneNumber;
}
