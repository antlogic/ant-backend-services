package com.ant.backendservices.controller;

import com.ant.backendservices.payload.request.auth.pair.CreatePairCodeRequest;
import com.ant.backendservices.payload.response.JwtAuthenticationResponse;
import com.ant.backendservices.payload.response.JwtPairResponse;
import com.ant.backendservices.payload.response.PairCodeResponse;
import com.ant.backendservices.service.PairDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/v1/auth/pair")
public class PairDeviceController {

    @Autowired
    private PairDeviceService pairDeviceService;

    @PostMapping
    public ResponseEntity<PairCodeResponse> createPairCode(@Valid @RequestBody CreatePairCodeRequest createPairCodeRequest) {
        return new ResponseEntity<>(pairDeviceService.generatePairCode(createPairCodeRequest), HttpStatus.OK);
    }

    @GetMapping("/{pairCode}")
    public ResponseEntity<JwtPairResponse> getDeviceAuthToken(@PathVariable String pairCode) {
        return new ResponseEntity<>(pairDeviceService.retrieveDeviceAuthToken(pairCode), HttpStatus.OK);
    }

}
