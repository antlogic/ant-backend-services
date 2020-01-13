package com.ant.backendservices.controller;

import com.ant.backendservices.model.User;
import com.ant.backendservices.payload.request.auth.LoginRequest;
import com.ant.backendservices.payload.request.auth.SignUpRequest;
import com.ant.backendservices.payload.response.RegisterResponse;
import com.ant.backendservices.payload.response.InvalidCredentialsResponse;
import com.ant.backendservices.payload.response.JwtAuthenticationResponse;
import com.ant.backendservices.security.JwtTokenProvider;
import com.ant.backendservices.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail().toLowerCase(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            log.error("Invalid credentials for user/email: {}", loginRequest.getUsernameOrEmail());
            InvalidCredentialsResponse invalidCredentialsResponse = new InvalidCredentialsResponse();
            invalidCredentialsResponse.setMessage("Invalid credentials for user/email: " + loginRequest.getUsernameOrEmail());
            return new ResponseEntity<>(invalidCredentialsResponse, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        jwtResponse.setAccessToken(jwt);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User result = userService.registerNewUser(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new RegisterResponse(true, "User registered successfully"));
    }
}
