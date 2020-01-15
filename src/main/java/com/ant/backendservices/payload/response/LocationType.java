package com.ant.backendservices.payload.response;

import lombok.Data;

import java.util.Date;

@Data
public class LocationType {
    private Long locationId;

    private String name;

    private String address;

    private Long phoneNumber;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;
}
