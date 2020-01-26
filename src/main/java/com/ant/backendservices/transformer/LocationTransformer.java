package com.ant.backendservices.transformer;

import com.ant.backendservices.bo.LocationBO;
import com.ant.backendservices.model.Company;
import com.ant.backendservices.model.Location;
import com.ant.backendservices.payload.request.location.CreateLocationRequest;
import com.ant.backendservices.payload.response.LocationType;
import com.ant.backendservices.payload.response.RetrieveLocationsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

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

    @Mappings({
            @Mapping(target = "locationId", source = "locationId")
    })
    LocationType locationEntityToLocationType(LocationBO location);

    List<LocationType> locationEntityListToLocationTypeList(List<LocationBO> locations);

    default RetrieveLocationsResponse locationEntityListToRetrieveLocationsResponse(List<LocationBO> locations) {
        RetrieveLocationsResponse response = new RetrieveLocationsResponse();
        response.setLocations(locationEntityListToLocationTypeList(locations));
        return response;
    }

    @Mappings({
            @Mapping(target = "locationId", source = "id")
    })
    LocationBO locationEntityToLocationBO(Location location);
}
