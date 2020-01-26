package com.ant.backendservices.controller;

import com.ant.backendservices.model.Slide;
import com.ant.backendservices.payload.request.slide.CreateSlideRequest;
import com.ant.backendservices.payload.response.RetrieveSlidesResponse;
import com.ant.backendservices.service.AuthService;
import com.ant.backendservices.service.SlideService;
import com.ant.backendservices.transformer.SlideTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/locations/{locationId}/displays/{displayId}/slides")
public class SlideController {

    @Autowired
    private SlideService slideService;

    @Autowired
    private SlideTransformer slideTransformer;

    @Autowired
    private AuthService authService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveSlidesResponse> getSlides(@PathVariable Long locationId, @PathVariable Long displayId) {
        Long companyId = authService.getLoggedInCompanyId();
        List<Slide> slides = slideService.getSlidesByCompanyId(companyId, locationId, displayId);
        RetrieveSlidesResponse response = slideTransformer.slideEntityListToRetrieveSlidesResponse(slides);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveSlidesResponse> createSlide(@Valid @RequestBody CreateSlideRequest createSlideRequest, @PathVariable Long locationId, @PathVariable Long displayId) {
        Long companyId = authService.getLoggedInCompanyId();
        slideService.createSlide(createSlideRequest, companyId, locationId, displayId);
        return getSlides(locationId, displayId);
    }


}
