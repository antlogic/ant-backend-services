package com.ant.backendservices.service;

import com.ant.backendservices.error.Error;
import com.ant.backendservices.exception.BadRequestException;
import com.ant.backendservices.model.Company;
import com.ant.backendservices.model.RoleName;
import com.ant.backendservices.model.User;
import com.ant.backendservices.payload.request.auth.SignUpRequest;
import com.ant.backendservices.repository.UserRepository;
import com.ant.backendservices.transformer.CompanyTransformer;
import com.ant.backendservices.transformer.UserTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyTransformer companyTransformer;

    @Autowired
    private UserTransformer userTransformer;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Transactional
    public User registerNewUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BadRequestException(Error.VALIDATION_ERROR, "Username already taken.");
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException(Error.VALIDATION_ERROR, "Email address already in use.");
        }

        Company company = companyTransformer.signUpRequestToCompanyEntity(signUpRequest);

        if (company == null) {
            throw new BadRequestException(Error.VALIDATION_ERROR, "Company information not provided. Company information required for user registration.");
        }


        if (company.getId() != null) {
            // Verify company for existing enrollment
            Company dbCompany = companyService.getCompanyById(company.getId());
            if (dbCompany == null) {
                throw new BadRequestException(Error.VALIDATION_ERROR, "Company Id" + company.getId() + "is invalid. Please provide a valid companyId or provide null value for companyId.");
            }
            company = dbCompany;
        } else {
            // Create new company
            company = companyService.createCompany(company);
        }

        User user = userTransformer.signUpRequestToUserEntity(signUpRequest, company, passwordEncoder, Collections.singleton(roleService.getRole(RoleName.ROLE_USER)));

        return userRepository.save(user);

    }


}
