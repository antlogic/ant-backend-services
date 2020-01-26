package com.ant.backendservices.bo;

import lombok.Data;

@Data
public class UserBO {
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String email;
}
