package com.ant.backendservices.controller;

import com.ant.backendservices.exception.AppException;
import com.ant.backendservices.model.Company;
import com.ant.backendservices.model.Role;
import com.ant.backendservices.model.RoleName;
import com.ant.backendservices.model.User;
import com.ant.backendservices.payload.*;
import com.ant.backendservices.repository.CompanyRepository;
import com.ant.backendservices.repository.RoleRepository;
import com.ant.backendservices.repository.UserRepository;
import com.ant.backendservices.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsernameOrEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            log.error("Invalid credentials for user/email: {}", loginRequest.getUsernameOrEmail());
            InvalidCredentialsResponse invalidCredentialsResponse = new InvalidCredentialsResponse();
            invalidCredentialsResponse.setMessage("Invalid credentials for user/email: " + loginRequest.getUsernameOrEmail());
            return ResponseEntity.ok(invalidCredentialsResponse);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse();
        jwtResponse.setAccessToken(jwt);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Create company account
        Company company = new Company()
                .setName(signUpRequest.getCompanyName())
                .setAddress(signUpRequest.getCompanyAddress());
        Company persistCompanyResult = companyRepository.save(company);

        // Creating user's account
        User user = new User()
                .setUsername(signUpRequest.getUsername())
                .setCompany(persistCompanyResult)
                .setEmail(signUpRequest.getEmail())
                .setPhoneNumber(signUpRequest.getPhoneNumber())
                .setFirstName(signUpRequest.getFirstName())
                .setLastName(signUpRequest.getLastName())
                .setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
