package com.ant.backendservices.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class RetrieveDisplaysResponse {

    List<DisplayType> displays;
}
