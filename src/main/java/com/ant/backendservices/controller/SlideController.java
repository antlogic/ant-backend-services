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
@RequestMapping("/v1")
public class SlideController {

    @Autowired
    private SlideService slideService;

    @Autowired
    private SlideTransformer slideTransformer;

    @Autowired
    private AuthService authService;

    @GetMapping("/locations/{locationId}/displays/{displayId}/slides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveSlidesResponse> getSpecificSlides(@PathVariable Long locationId, @PathVariable Long displayId) {
        Long companyId = authService.getLoggedInCompanyId();
        List<Slide> slides = slideService.getSlidesByCompanyIdLocationIdDisplayId(companyId, locationId, displayId);
        RetrieveSlidesResponse response = slideTransformer.slideEntityListToRetrieveSlidesResponse(slides);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/locations/{locationId}/displays/{displayId}/slides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveSlidesResponse> createSlide(@Valid @RequestBody CreateSlideRequest createSlideRequest, @PathVariable Long locationId, @PathVariable Long displayId) {
        Long companyId = authService.getLoggedInCompanyId();
        slideService.createSpecificSlide(createSlideRequest, companyId, locationId, displayId);
        return getSpecificSlides(locationId, displayId);
    }

    @GetMapping("/slides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveSlidesResponse> getCompanySlides() {
        Long companyId = authService.getLoggedInCompanyId();
        List<Slide> slides = slideService.getSlidesByCompanyId(companyId);
        RetrieveSlidesResponse response = slideTransformer.slideEntityListToRetrieveSlidesResponse(slides);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/slides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveSlidesResponse> createCompanySlides(@Valid @RequestBody CreateSlideRequest createSlideRequest) {
        Long companyId = authService.getLoggedInCompanyId();
        slideService.createCompanySlide(createSlideRequest, companyId);
        return getCompanySlides();
    }

    @PatchMapping("/locations/{locationId}/displays/{displayId}/slides/{slideId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveSlidesResponse> updateSlide(@Valid @RequestBody CreateSlideRequest createSlideRequest, @PathVariable String locationId, @PathVariable String displayId, @PathVariable String slideId) {
        Long companyId = authService.getLoggedInCompanyId();

    }
}
