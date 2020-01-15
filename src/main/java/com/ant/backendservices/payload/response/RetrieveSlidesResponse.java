package com.ant.backendservices.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class RetrieveSlidesResponse {
    List<SlideType> slides;
}
