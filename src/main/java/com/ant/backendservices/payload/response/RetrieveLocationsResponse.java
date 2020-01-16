package com.ant.backendservices.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class RetrieveLocationsResponse {
    List<LocationType> locations;
}
