package com.ant.backendservices.bo;

import lombok.Data;

import java.util.Date;

@Data
public class DisplayBO {
    private Long displayId;

    private Long locationId;

    private String name;

    private String description;

    private Long transitionTimeMillis;

    private String orientation;

    private Integer numberOfSlides;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;
}
