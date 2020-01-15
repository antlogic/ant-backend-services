package com.ant.backendservices.payload.request.slide;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateSlideRequest {

    @NotBlank
    private String name;

    @Size(max = 50)
    private String type;

    private String imageUrl;

    @NotNull
    private Long locationId;

    @NotNull
    private Long displayId;

}
