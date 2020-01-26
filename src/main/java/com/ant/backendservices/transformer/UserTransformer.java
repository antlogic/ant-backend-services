package com.ant.backendservices.transformer;

import com.ant.backendservices.bo.UserBO;
import com.ant.backendservices.model.Company;
import com.ant.backendservices.model.Role;
import com.ant.backendservices.model.User;
import com.ant.backendservices.payload.request.auth.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserTransformer {

    @Mappings({
            @Mapping(target = "firstName", source = "signUpRequest.firstName"),
            @Mapping(target = "lastName", source = "signUpRequest.lastName"),
            @Mapping(target = "email", expression = "java(signUpRequest.getEmail().toLowerCase())"),
            @Mapping(target = "username", expression = "java(signUpRequest.getUsername().toLowerCase())"),
            @Mapping(target = "password", expression = "java(passwordEncoder.encode(signUpRequest.getPassword()))"),
            @Mapping(target = "phoneNumber", source = "signUpRequest.phoneNumber"),
            @Mapping(target = "roles", source = "roleSet"),
            @Mapping(target = "company", source = "company"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    User signUpRequestToUserEntity(SignUpRequest signUpRequest, Company company, PasswordEncoder passwordEncoder, Set<Role> roleSet);

    UserBO userEntityToUserBO(User user);
}
