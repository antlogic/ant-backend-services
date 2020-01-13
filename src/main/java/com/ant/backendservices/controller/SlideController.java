package com.ant.backendservices.controller;

import com.ant.backendservices.model.Slide;
import com.ant.backendservices.repository.SlideRepository;
import com.ant.backendservices.service.AuthService;
import com.ant.backendservices.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/slides")
public class SlideController {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Slide>> getSlides() {
        Long companyId = authService.getLoggedInCompanyId();
        List<Slide> slides = slideRepository.getSlidesByCompanyId(companyId).orElse(null);
        return new ResponseEntity<>(slides, HttpStatus.OK);
    }


}
