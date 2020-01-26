package com.ant.backendservices.controller;

import com.ant.backendservices.bo.LocationBO;
import com.ant.backendservices.model.Location;
import com.ant.backendservices.payload.request.location.CreateLocationRequest;
import com.ant.backendservices.payload.response.RegisterResponse;
import com.ant.backendservices.payload.response.RetrieveLocationsResponse;
import com.ant.backendservices.service.AuthService;
import com.ant.backendservices.service.LocationService;
import com.ant.backendservices.transformer.LocationTransformer;
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
@RequestMapping("/v1/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationTransformer locationTransformer;

    @Autowired
    private AuthService authService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveLocationsResponse> createLocation(@Valid @RequestBody CreateLocationRequest createLocationRequest) {
        Long companyId = authService.getLoggedInCompanyId();
        locationService.createLocation(createLocationRequest, companyId);
        return getLocations();
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveLocationsResponse> getLocations() {
        Long companyId = authService.getLoggedInCompanyId();
        List<LocationBO> locations = locationService.getLocations(companyId);
        RetrieveLocationsResponse response = locationTransformer.locationEntityListToRetrieveLocationsResponse(locations);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
