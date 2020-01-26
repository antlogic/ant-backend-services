package com.ant.backendservices.controller;

import com.ant.backendservices.bo.UserBO;
import com.ant.backendservices.model.User;
import com.ant.backendservices.payload.request.auth.LoginRequest;
import com.ant.backendservices.payload.request.auth.SignUpRequest;
import com.ant.backendservices.payload.response.RegisterResponse;
import com.ant.backendservices.payload.response.JwtAuthenticationResponse;
import com.ant.backendservices.security.JwtTokenProvider;
import com.ant.backendservices.service.AuthService;
import com.ant.backendservices.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Value("${app.jwtExpirationInMs}")
    private Long jwtExpiry;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication = authService.signIn(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
        UserBO user = userService.getUser(loginRequest.getUsernameOrEmail());

        String jwt = tokenProvider.generateToken(authentication);
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        jwtResponse.setAccessToken(jwt);
        jwtResponse.setAccessTokenExpiry(jwtExpiry);
        jwtResponse.setFirstName(user.getFirstName());
        jwtResponse.setLastName(user.getLastName());

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
