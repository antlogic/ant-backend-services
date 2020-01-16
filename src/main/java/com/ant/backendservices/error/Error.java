package com.ant.backendservices.error;

import lombok.Getter;

@Getter
public enum Error {

    /**
     * Global Application Errors
     */
    AUTHORIZATION_ERROR(101, ErrorCategory.AUTHORIZATION_ERROR, "Unauthorized access. Please provide valid credentials."),
    VALIDATION_ERROR(102, ErrorCategory.VALIDATION_ERROR, "Invalid request. Please refer to /v2/api-docs."),
    INTERNAL_SERVER_ERROR(103, ErrorCategory.INTERNAL_SERVER_ERROR, "An Internal Server Error occurred. Please try again later. If issue persists, please contact engineering team."),
    ;

    private Integer errorCode;
    private ErrorCategory errorCategory;
    private String errorMessage;
    private String moreInfo;

    Error(Integer errorCode, ErrorCategory errorCategory) {
        this.errorCode = errorCode;
        this.errorCategory = errorCategory;
    }

    Error(Integer errorCode, ErrorCategory errorCategory, String errorMessage) {
        this.errorCode = errorCode;
        this.errorCategory = errorCategory;
        this.errorMessage = errorMessage;
    }

    Error(Integer errorCode, ErrorCategory errorCategory, String errorMessage, String moreInfo) {
        this.errorCode = errorCode;
        this.errorCategory = errorCategory;
        this.errorMessage = errorMessage;
        this.moreInfo = moreInfo;
    }

}
