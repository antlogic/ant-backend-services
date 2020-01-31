package com.ant.backendservices.service;

import com.ant.backendservices.model.*;
import com.ant.backendservices.payload.request.slide.CreateSlideRequest;
import com.ant.backendservices.repository.SlideRepository;
import com.ant.backendservices.transformer.SlideTransformer;
import lombok.extern.slf4j.Slf4j;
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
        return slideRepository.findByCompanyIdAndLocationIdAndDisplayId(companyId, locationId, displayId).orElse(null);
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

        Slide slide = slideTransformer.createSlideRequestToSlideEntity(createSlideRequest, image, display,
                location, company);

        return slideRepository.save(slide);
    }

    @Transactional
    public Slide createCompanySlide(CreateSlideRequest createSlideRequest, Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        Image image = imageService.getImageById(createSlideRequest.getImageId());

        Slide slide = slideTransformer.createSlideRequestToSlideEntity(createSlideRequest, image, null, null, company);
        return slideRepository.save(slide);
    }



}
