package com.ant.backendservices.transformer;

import com.ant.backendservices.error.Error;
import com.ant.backendservices.payload.response.ErrorResponse;
import com.ant.backendservices.payload.response.ErrorType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorTransformer {

    public ErrorType errorToErrorType(Error error) {
        if (error == null) {
            return null;
        }

        ErrorType errorType = new ErrorType();
        errorType.setErrorCode(error.getErrorCode());
        errorType.setMessage(error.getErrorMessage());
        errorType.setMoreInfo(error.getMoreInfo());
        return errorType;
    }

    public ErrorResponse errorListToErrorResponse(Error error) {
        if (error == null) {
            return null;
        }

        ErrorResponse response = new ErrorResponse();
        response.setErrors(new ArrayList<>());
        response.getErrors().add(errorToErrorType(error));
        return response;
    }
}
