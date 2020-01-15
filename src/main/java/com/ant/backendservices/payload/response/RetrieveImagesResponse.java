package com.ant.backendservices.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class RetrieveImagesResponse {
    List<ImageType> images;
}
