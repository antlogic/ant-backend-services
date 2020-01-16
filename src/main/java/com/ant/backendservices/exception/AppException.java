package com.ant.backendservices.exception;

import com.ant.backendservices.error.Error;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

    private transient Error error;
    private String message;

    public AppException(Error error, String message) {
        this.error = error;
        this.message = message;
    }

    public AppException(Error error, String message, Throwable cause) {
        super(cause);
        this.error = error;
        this.message = message;
    }

}
