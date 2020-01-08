package com.ant.backendservices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping
    public ResponseEntity<String> getPublicResponse() {
        return new ResponseEntity<>("hello public", HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/secure")
    public ResponseEntity<String> getAuthResponse() {
        return new ResponseEntity<>("hello GOD", HttpStatus.OK);
    }

}
