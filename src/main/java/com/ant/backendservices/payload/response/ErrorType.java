package com.ant.backendservices.payload.response;

import lombok.Data;

@Data
public class ErrorType {
    private Integer errorCode;
    private String message;
    private String moreInfo;
}
