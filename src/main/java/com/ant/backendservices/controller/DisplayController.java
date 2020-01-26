package com.ant.backendservices.controller;

import com.ant.backendservices.bo.DisplayBO;
import com.ant.backendservices.payload.request.display.RegisterDisplayRequest;
import com.ant.backendservices.payload.response.RetrieveDisplaysResponse;
import com.ant.backendservices.service.AuthService;
import com.ant.backendservices.service.DisplayService;
import com.ant.backendservices.service.PairDeviceService;
import com.ant.backendservices.transformer.DisplayTransformer;
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
@RequestMapping("/v1/locations/{locationId}/displays")
public class DisplayController {

    @Autowired
    private DisplayService displayService;

    @Autowired
    private DisplayTransformer displayTransformer;

    @Autowired
    private PairDeviceService pairDeviceService;

    @Autowired
    private AuthService authService;

    @PostMapping("/link")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveDisplaysResponse> registerNewDisplay(@PathVariable ("locationId") Long locationId, @Valid @RequestBody RegisterDisplayRequest registerDisplayRequest) {
        pairDeviceService.pairDeviceToDisplay(registerDisplayRequest, locationId);
        return getDisplays(locationId);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveDisplaysResponse> getDisplays(@PathVariable ("locationId") Long locationId) {
        Long companyId = authService.getLoggedInCompanyId();
        List<DisplayBO> displays = displayService.getDisplays(companyId, locationId);
        RetrieveDisplaysResponse response = displayTransformer.displayEntityListToRetrieveDisplaysResponse(displays);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
