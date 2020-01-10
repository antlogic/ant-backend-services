package com.ant.backendservices.controller;

import com.ant.backendservices.payload.SecuredResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping
    public ResponseEntity<SecuredResponse> getPublicResponse() {
        SecuredResponse securedResponse = new SecuredResponse();
        securedResponse.setResponse("hello public");
        return new ResponseEntity<>(securedResponse, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/secure")
    public ResponseEntity<SecuredResponse> getAuthResponse() {
        SecuredResponse securedResponse = new SecuredResponse();
        securedResponse.setResponse("hello GOD");
        return new ResponseEntity<>(securedResponse, HttpStatus.OK);
    }

}
