package com.ant.backendservices.exception;

import com.ant.backendservices.error.Error;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AuthorizationException extends RuntimeException {

    private transient Error error;
    private String message;

    public AuthorizationException(Error error, String message) {
        this.error = error;
        this.message = message;
    }

    public AuthorizationException(Error error, String message, Throwable cause) {
        super(cause);
        this.error = error;
        this.message = message;
    }

}
