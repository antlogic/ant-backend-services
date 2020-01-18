package com.ant.backendservices.service;

import com.ant.backendservices.error.Error;
import com.ant.backendservices.exception.AuthorizationException;
import com.ant.backendservices.model.User;
import com.ant.backendservices.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String getLoggedInUser() {
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }
        return username;
    }

    @Transactional
    public Long getLoggedInCompanyId() {
        String username = getLoggedInUser();
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            return null;
        }

        return user.getCompany() != null ? user.getCompany().getId() : null;
    }

    public Authentication signIn(String usernameOrEmail, String password) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usernameOrEmail.toLowerCase(),
                            password
                    )
            );
        } catch (BadCredentialsException ex) {
            log.error("Invalid credentials for user/email: {}", usernameOrEmail.toLowerCase());
            throw new AuthorizationException(Error.AUTHORIZATION_ERROR, "Invalid username or password.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
