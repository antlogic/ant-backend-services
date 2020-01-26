package com.ant.backendservices.bo;

import lombok.Data;

import java.util.Date;

@Data
public class LocationBO {

    private Long locationId;

    private String name;

    private String address;

    private Long phoneNumber;

    private Integer numberOfDisplays;

    private Date createdAt;

    private String createdBy;

    private Date updatedAt;

    private String updatedBy;

}
