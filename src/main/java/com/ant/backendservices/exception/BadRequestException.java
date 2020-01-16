package com.ant.backendservices.exception;

import com.ant.backendservices.error.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private transient Error error;
    private String message;

    public BadRequestException(Error error, String message) {
        this.error = error;
        this.message = message;
    }

    public BadRequestException(Error error, String message, Throwable cause) {
        super(cause);
        this.error = error;
        this.message = message;
    }
}
