package com.ant.backendservices.validator;

import com.ant.backendservices.exception.BadRequestException;
import com.ant.backendservices.payload.request.display.RegisterDisplayRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DisplayRequestValidator {

    private static String HORIZONTAL = "HORIZONTAL";
    private static String VERTICAL = "VERTICAL";

    public void validate(RegisterDisplayRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body can not be null.");
        }

        if (!StringUtils.equalsIgnoreCase(request.getOrientation(), HORIZONTAL) &&
            !StringUtils.equalsIgnoreCase(request.getOrientation(), VERTICAL)) {
            throw new BadRequestException("Orientation can only be of two types: HORIZONTAL or VERTICAL");
        }
    }
}
