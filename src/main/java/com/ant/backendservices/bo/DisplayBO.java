package com.ant.backendservices.bo;

import lombok.Data;

import java.util.Date;

@Data
public class DisplayBO {
    private Long displayId;

    private Long locationId;

    private String name;

    private String description;

    private String model;

    private String manufacturer;

    private Boolean isLandscape;

    private Integer numberOfSlides;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;
}
