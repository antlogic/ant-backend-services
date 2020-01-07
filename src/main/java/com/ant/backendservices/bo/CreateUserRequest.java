package com.ant.backendservices.bo;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Long phoneNumber;
    private String email;
    private String role;
    private String companyName;
}
