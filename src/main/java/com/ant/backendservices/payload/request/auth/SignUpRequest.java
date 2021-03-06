package com.ant.backendservices.payload.request.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String firstName;

    @NotBlank
    @Size(min = 4, max = 40)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotNull
    private Long phoneNumber;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    private Long companyId;

    @NotBlank
    @Size(min = 1, max = 15)
    private String companyName;

    @Size(max = 50)
    private String companyAddress;
}
