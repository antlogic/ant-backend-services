package com.ant.backendservices.controller;

import com.ant.backendservices.model.Display;
import com.ant.backendservices.payload.request.display.RegisterDisplayRequest;
import com.ant.backendservices.payload.response.RegisterResponse;
import com.ant.backendservices.repository.DisplayRepository;
import com.ant.backendservices.service.AuthService;
import com.ant.backendservices.service.DisplayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController()
@RequestMapping("/displays")
public class DisplayController {

    @Autowired
    private DisplayService displayService;

    @Autowired
    private AuthService authService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RegisterResponse> registerNewDisplay(@Valid @RequestBody RegisterDisplayRequest registerDisplayRequest) {
        Long companyId = authService.getLoggedInCompanyId();
        Display display = displayService.createDisplay(registerDisplayRequest, companyId);

        if (display == null) {
            return new ResponseEntity<>(new RegisterResponse(false, "Display registration failed."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new RegisterResponse(true, "Display registered successfully."), HttpStatus.OK);
    }
}
