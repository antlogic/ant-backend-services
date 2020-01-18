package com.ant.backendservices.exception.handler;

import com.ant.backendservices.exception.AppException;
import com.ant.backendservices.exception.AuthorizationException;
import com.ant.backendservices.exception.BadRequestException;
import com.ant.backendservices.payload.response.ErrorResponse;
import com.ant.backendservices.transformer.ErrorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ErrorTransformer errorTransformer;

    @ExceptionHandler(value = AppException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAppException(HttpServletRequest request, AppException e) {
        ErrorResponse response = errorTransformer.errorListToErrorResponse(e.getError());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (response != null) {
            status = e.getError().getErrorCategory() != null ? e.getError().getErrorCategory().getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleBadRequestException(HttpServletRequest request, BadRequestException e) {
        ErrorResponse response = errorTransformer.errorListToErrorResponse(e.getError());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (response != null) {
            status = e.getError().getErrorCategory() != null ? e.getError().getErrorCategory().getHttpStatus() : HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleAuthorizationException(HttpServletRequest request, AuthorizationException e) {
        ErrorResponse response = errorTransformer.errorListToErrorResponse(e.getError());
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        if (response != null) {
            status = e.getError().getErrorCategory() != null ? e.getError().getErrorCategory().getHttpStatus() : HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(response, status);
    }
}
