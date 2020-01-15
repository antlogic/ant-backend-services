package com.ant.backendservices.service;

import com.ant.backendservices.exception.AppException;
import com.ant.backendservices.model.Company;
import com.ant.backendservices.model.Location;
import com.ant.backendservices.payload.request.location.CreateLocationRequest;
import com.ant.backendservices.repository.LocationRepository;
import com.ant.backendservices.transformer.LocationTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTransformer locationTransformer;

    @Autowired
    private CompanyService companyService;

    @Transactional
    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Transactional
    public Location createLocation(CreateLocationRequest createLocationRequest, Long companyId) {
        Company company = companyService.getCompanyById(companyId);

        if (company == null) {
            throw new AppException("Invalid companyId {}" + companyId + ".");
        }

        Location location = locationTransformer.createLocationRequestToLocationEntity(createLocationRequest, company);

        return locationRepository.save(location);
    }
}
