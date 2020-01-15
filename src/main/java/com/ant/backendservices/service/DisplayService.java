package com.ant.backendservices.service;

import com.ant.backendservices.exception.AppException;
import com.ant.backendservices.model.Company;
import com.ant.backendservices.model.Display;
import com.ant.backendservices.model.Location;
import com.ant.backendservices.payload.request.display.RegisterDisplayRequest;
import com.ant.backendservices.repository.DisplayRepository;
import com.ant.backendservices.transformer.DisplayTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisplayService {

    @Autowired
    private DisplayRepository displayRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DisplayTransformer displayTransformer;

    @Transactional
    public Display createDisplay(RegisterDisplayRequest registerDisplayRequest, Long companyId, Long locationId) {
        Company company = companyService.getCompanyById(companyId);

        if (company == null) {
            throw new AppException("Invalid companyId {}" + companyId + ".");
        }

        Location location = locationService.getLocationById(locationId);

        if (location == null) {
            throw new AppException("Invalid locationId {}" + locationId + ".");
        }

        Display display = displayTransformer.registerDisplayRequestToDisplayEntity(registerDisplayRequest, location, company);

        return displayRepository.save(display);
    }

    public Display getDisplayById(Long id) {
        return displayRepository.findById(id).orElse(null);
    }

    public List<Display> getDisplays(Long companyId, Long locationId) {
        List<Display> displays = displayRepository.findByCompanyIdAndLocationId(companyId, locationId).orElse(null);
        return displays;
    }


}
