package com.ant.backendservices.service;

import com.ant.backendservices.error.Error;
import com.ant.backendservices.exception.AppException;
import com.ant.backendservices.model.*;
import com.ant.backendservices.payload.request.slide.CreateSlideRequest;
import com.ant.backendservices.repository.SlideRepository;
import com.ant.backendservices.transformer.SlideTransformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SlideService {

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DisplayService displayService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SlideTransformer slideTransformer;

    public List<Slide> getSlidesByCompanyIdLocationIdDisplayId(Long companyId, Long locationId, Long displayId) {
        return slideRepository.findByCompanyIdAndLocations_IdAndDisplays_Id(companyId, locationId, displayId).orElse(null);
    }

    public List<Slide> getSlidesByCompanyId(Long companyId) {
        return slideRepository.findByCompanyId(companyId).orElse(null);
    }

    @Transactional
    public Slide createSpecificSlide(CreateSlideRequest createSlideRequest, Long companyId, Long locationId, Long displayId) {
        Company company = companyService.getCompanyById(companyId);
        Location location = locationService.getLocationById(locationId);
        Display display = displayService.getDisplayById(displayId);
        Image image = imageService.getImageById(createSlideRequest.getImageId());

        //TODO: Need to do validations on objects and their ids

        Slide slide = slideTransformer.createSlideRequestToSlideEntity(createSlideRequest, image, company);
        slide.getDisplays().add(display);
        slide.getLocations().add(location);

        return slideRepository.save(slide);
    }

    @Transactional
    public Slide createCompanySlide(CreateSlideRequest createSlideRequest, Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        Image image = imageService.getImageById(createSlideRequest.getImageId());

        Slide slide = slideTransformer.createSlideRequestToSlideEntity(createSlideRequest, image, company);
        return slideRepository.save(slide);
    }

    @Transactional
    public Slide associateSlideToDisplayAndLocation(Long companyId, Long locationId, Long displayId, Long slideId) {
        Slide slide = slideRepository.findById(slideId).orElse(null);

        if(slide == null) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Slide not found for id " + slideId);
        }
        if(ObjectUtils.compare(slide.getCompany().getId(), companyId) != 0){
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Slide not found for id " + slideId + " in company with id " + companyId);
        }

        Display display = displayService.getDisplayById(displayId);
        if(display == null) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Display not found for id " + displayId);
        }

        Location location = locationService.getLocationById(locationId);
        if(location == null) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Location not found for id " + locationId);
        }

        slide.getDisplays().add(display);
        slide.getLocations().add(location);

        return slideRepository.save(slide);
    }



}
