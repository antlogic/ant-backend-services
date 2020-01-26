package com.ant.backendservices.transformer;

import com.ant.backendservices.model.*;
import com.ant.backendservices.payload.request.slide.CreateSlideRequest;
import com.ant.backendservices.payload.response.RetrieveSlidesResponse;
import com.ant.backendservices.payload.response.SlideType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = ImageTransformer.class)
public interface SlideTransformer {

    @Mappings({
            @Mapping(target = "name", source = "createSlideRequest.name"),
            @Mapping(target = "type", source = "createSlideRequest.type"),
            @Mapping(target = "transitionTimeMillis", source = "createSlideRequest.transitionTimeMillis", defaultExpression = "java(1000L)"),
            @Mapping(target = "display", source = "display"),
            @Mapping(target = "location", source = "location"),
            @Mapping(target = "company", source = "company"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "updatedBy", ignore = true)
    })
    Slide createSlideRequestToSlideEntity(CreateSlideRequest createSlideRequest, Image image, Display display, Location location, Company company);

    @Mappings({
            @Mapping(target = "slideId", source = "id"),
            @Mapping(target = "displayId", source = "display.id"),
            @Mapping(target = "locationId", source = "location.id"),
            @Mapping(target = "image", source = "image")
    })
    SlideType slideEntityToSlideType(Slide slide);

    List<SlideType> slideEntityListToSlideTypeList(List<Slide> slides);

    default RetrieveSlidesResponse slideEntityListToRetrieveSlidesResponse(List<Slide> slides) {
        RetrieveSlidesResponse response = new RetrieveSlidesResponse();
        response.setSlides(slideEntityListToSlideTypeList(slides));
        return response;
    }
}
