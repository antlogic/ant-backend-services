package com.ant.backendservices.transformer;

import com.ant.backendservices.model.PairDevice;
import com.ant.backendservices.payload.request.auth.pair.CreatePairCodeRequest;
import com.ant.backendservices.payload.response.PairCodeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PairDeviceTransformer {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "displayId", ignore = true),
            @Mapping(target = "deviceId", expression = "java(java.util.UUID.randomUUID().toString())"),
            @Mapping(target = "pairCode", expression = "java(org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(6).toUpperCase())"),
            @Mapping(target = "pairedStatus", expression = "java(false)")
    })
    PairDevice createPairCodeRequestToPairDeviceEntity(CreatePairCodeRequest createPairCodeRequest);

    @Mappings({
        @Mapping(target = "pairCode", source = "pairCode")
    })
    PairCodeResponse pairDeviceEntityToPairCodeResponse(PairDevice pairDevice);
}
