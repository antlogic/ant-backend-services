package com.ant.backendservices.controller;

import com.ant.backendservices.model.Display;
import com.ant.backendservices.payload.request.display.RegisterDisplayRequest;
import com.ant.backendservices.payload.response.RegisterResponse;
import com.ant.backendservices.payload.response.RetrieveDisplaysResponse;
import com.ant.backendservices.service.AuthService;
import com.ant.backendservices.service.DisplayService;
import com.ant.backendservices.transformer.DisplayTransformer;
import com.ant.backendservices.validator.DisplayRequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/locations/{locationId}/displays")
public class DisplayController {

    @Autowired
    private DisplayService displayService;

    @Autowired
    private DisplayTransformer displayTransformer;

    @Autowired
    private AuthService authService;

    @Autowired
    private DisplayRequestValidator displayRequestValidator;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RegisterResponse> registerNewDisplay(@PathVariable ("locationId") Long locationId, @Valid @RequestBody RegisterDisplayRequest registerDisplayRequest) {
        displayRequestValidator.validate(registerDisplayRequest);
        Long companyId = authService.getLoggedInCompanyId();
        Display display = displayService.createDisplay(registerDisplayRequest, companyId, locationId);

        if (display == null) {
            return new ResponseEntity<>(new RegisterResponse(false, "Display registration failed."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new RegisterResponse(true, "Display registered successfully."), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveDisplaysResponse> getDisplays(@PathVariable ("locationId") Long locationId) {
        Long companyId = authService.getLoggedInCompanyId();
        List<Display> displays = displayService.getDisplays(companyId, locationId);
        RetrieveDisplaysResponse response = displayTransformer.displayEntityListToRetrieveDisplaysResponse(displays);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
