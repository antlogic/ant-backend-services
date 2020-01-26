package com.ant.backendservices.service;

import com.ant.backendservices.bo.LocationBO;
import com.ant.backendservices.error.Error;
import com.ant.backendservices.exception.AppException;
import com.ant.backendservices.model.Company;
import com.ant.backendservices.model.Location;
import com.ant.backendservices.payload.request.location.CreateLocationRequest;
import com.ant.backendservices.repository.LocationRepository;
import com.ant.backendservices.transformer.LocationTransformer;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private DisplayService displayService;

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
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Invalid companyId {}" + companyId + ".");
        }

        Location location = locationTransformer.createLocationRequestToLocationEntity(createLocationRequest, company);

        return locationRepository.save(location);
    }

    @Transactional
    public List<LocationBO> getLocations(Long companyId) {
        List<LocationBO> locationBOs = new ArrayList<>();
        List<Location> locations = locationRepository.findByCompanyId(companyId).orElse(null);
        CollectionUtils.emptyIfNull(locations).forEach(location -> {
            LocationBO locationBO = locationTransformer.locationEntityToLocationBO(location);
            locationBO.setNumberOfDisplays(CollectionUtils.emptyIfNull(displayService.getDisplays(companyId, location.getId())).size());
            locationBOs.add(locationBO);
        });

        return locationBOs;
    }
}
