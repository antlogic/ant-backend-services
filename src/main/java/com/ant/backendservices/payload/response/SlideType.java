package com.ant.backendservices.payload.response;

import lombok.Data;

import java.util.Date;

@Data
public class SlideType {
    private Long slideId;
    private Long displayId;
    private Long locationId;
    private String name;
    private String type;
    private ImageType image;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
}
