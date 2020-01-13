package com.ant.backendservices.service;

import com.ant.backendservices.model.User;
import com.ant.backendservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

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
}
