package com.ant.backendservices.controller;

import com.ant.backendservices.bo.CreateUserRequest;
import com.ant.backendservices.bo.CreateUserResponse;
import com.ant.backendservices.repository.CompanyRepository;
import com.ant.backendservices.repository.UserRepository;
import com.ant.backendservices.repository.entities.CompanyEntity;
import com.ant.backendservices.repository.entities.UserEntity;
import com.ant.backendservices.requestcontext.ApiContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ApiContext apiContext;

    @PostMapping(value = "/user", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName(request.getCompanyName());

        CompanyEntity persistedCompany = companyRepository.save(companyEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(request.getFirstName());
        userEntity.setLastName(request.getLastName());
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(request.getPassword());
        userEntity.setEmail(request.getEmail());
        userEntity.setPhoneNumber(request.getPhoneNumber());
        userEntity.setRole(request.getRole());
        userEntity.setCompany(persistedCompany);

        UserEntity persistedUser = userRepository.save(userEntity);
        CreateUserResponse response = new CreateUserResponse();
        response.setUserId(persistedUser.getId());
        response.setCompanyId(persistedUser.getCompany().getId());
        return new ResponseEntity<>(persistedUser.getId().toString(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserEntity> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userRepository.findById(id).orElse(null), HttpStatus.OK);
    }
}
