package com.ant.backendservices.payload.response;

import lombok.Data;

@Data
public class ImageType {
    private String fileId;
    private String fileName;
    private Long height;
    private Long width;
    // Size in bytes
    private Long size;
}
