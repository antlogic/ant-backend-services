package com.ant.backendservices.transformer;

import com.ant.backendservices.model.Company;
import com.ant.backendservices.model.Location;
import com.ant.backendservices.payload.request.location.CreateLocationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LocationTransformer {

    @Mappings({
            @Mapping(target = "name", source = "createLocationRequest.name"),
            @Mapping(target = "address", source = "createLocationRequest.address"),
            @Mapping(target = "phoneNumber", source = "createLocationRequest.phoneNumber"),
            @Mapping(target = "company", source = "company"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "updatedBy", ignore = true)
    })
    Location createLocationRequestToLocationEntity(CreateLocationRequest createLocationRequest, Company company);
}
