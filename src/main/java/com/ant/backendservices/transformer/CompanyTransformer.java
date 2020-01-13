package com.ant.backendservices.transformer;

import com.ant.backendservices.model.Company;
import com.ant.backendservices.payload.request.auth.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CompanyTransformer {

    @Mappings({
            @Mapping(target = "name", source = "companyName"),
            @Mapping(target = "address", source = "companyAddress"),
            @Mapping(target = "id", source = "companyId"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    Company signUpRequestToCompanyEntity(SignUpRequest signUpRequest);
}
