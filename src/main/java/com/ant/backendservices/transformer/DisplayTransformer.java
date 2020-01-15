package com.ant.backendservices.transformer;

import com.ant.backendservices.model.Company;
import com.ant.backendservices.model.Display;
import com.ant.backendservices.model.Location;
import com.ant.backendservices.payload.request.display.RegisterDisplayRequest;
import com.ant.backendservices.payload.response.DisplayType;
import com.ant.backendservices.payload.response.RetrieveDisplaysResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DisplayTransformer {

    @Mappings({
            @Mapping(target = "name", source = "registerDisplayRequest.name"),
            @Mapping(target = "description", source = "registerDisplayRequest.description"),
            @Mapping(target = "orientation", source = "registerDisplayRequest.orientation"),
            @Mapping(target = "transitionTimeMillis", source = "registerDisplayRequest.transitionTimeMillis"),
            @Mapping(target = "location", source = "location"),
            @Mapping(target = "company", source = "company"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "updatedBy", ignore = true)
    })
    Display registerDisplayRequestToDisplayEntity(RegisterDisplayRequest registerDisplayRequest, Location location, Company company);

    @Mappings({
            @Mapping(target = "displayId", source = "id"),
            @Mapping(target = "locationId", source = "location.id")
    })
    DisplayType displayEntityToDisplayType(Display display);

    List<DisplayType> displayEntityListToDisplayTypeList(List<Display> displays);

    default RetrieveDisplaysResponse displayEntityListToRetrieveDisplaysResponse(List<Display> displays) {
        RetrieveDisplaysResponse response = new RetrieveDisplaysResponse();
        response.setDisplays(displayEntityListToDisplayTypeList(displays));
        return response;
    }
}
