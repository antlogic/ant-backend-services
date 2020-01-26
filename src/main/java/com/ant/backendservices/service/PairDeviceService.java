package com.ant.backendservices.service;

import com.ant.backendservices.error.Error;
import com.ant.backendservices.exception.AppException;
import com.ant.backendservices.exception.BadRequestException;
import com.ant.backendservices.model.Display;
import com.ant.backendservices.model.Location;
import com.ant.backendservices.model.PairDevice;
import com.ant.backendservices.model.User;
import com.ant.backendservices.payload.request.auth.pair.CreatePairCodeRequest;
import com.ant.backendservices.payload.request.display.RegisterDisplayRequest;
import com.ant.backendservices.payload.response.JwtPairResponse;
import com.ant.backendservices.payload.response.PairCodeResponse;
import com.ant.backendservices.payload.response.RetrieveDisplaysResponse;
import com.ant.backendservices.repository.CompanyRepository;
import com.ant.backendservices.repository.DisplayRepository;
import com.ant.backendservices.repository.LocationRepository;
import com.ant.backendservices.repository.PairDeviceRepository;
import com.ant.backendservices.security.CustomUserDetailsService;
import com.ant.backendservices.security.JwtTokenProvider;
import com.ant.backendservices.transformer.PairDeviceTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PairDeviceService {

    @Autowired
    private PairDeviceRepository pairDeviceRepository;

    @Autowired
    private DisplayRepository displayRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PairDeviceTransformer pairDeviceTransformer;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    public PairCodeResponse generatePairCode(CreatePairCodeRequest createPairCodeRequest) {
        PairDevice pairDevice = pairDeviceTransformer.createPairCodeRequestToPairDeviceEntity(createPairCodeRequest);
        PairDevice dbEntity = pairDeviceRepository.save(pairDevice);
        return pairDeviceTransformer.pairDeviceEntityToPairCodeResponse(dbEntity);
    }

    public JwtPairResponse retrieveDeviceAuthToken(String pairCode) throws BadRequestException {
        PairDevice pairDevice = pairDeviceRepository.findByPairCode(pairCode)
                .orElseThrow(() -> new BadRequestException(Error.INVALID_PAIR_CODE, "Pair Code: " + pairCode + " is invalid."));

        JwtPairResponse response = new JwtPairResponse();
        if (!pairDevice.getPairedStatus()) {
            response.setIsRegistered(false);
            return response;
        }

        String jwtToken = jwtTokenProvider.generateDisplayDeviceToken(pairDevice.getUserId());
        response.setAccessToken(jwtToken);
        response.setIsRegistered(true);
        return response;
    }

    public Display pairDeviceToDisplay(RegisterDisplayRequest registerDisplayRequest, Long locationId) {
        User user = authService.getLoggedInUser();
        if (user == null || user.getCompany() == null) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Could not retrieve user/company.");
        }

        Long userId = user.getId();

        PairDevice pairDevice = pairDeviceRepository.findByPairCode(registerDisplayRequest.getPairCode()).orElse(null);
        if (pairDevice == null) {
            throw new BadRequestException(Error.INVALID_PAIR_CODE, "Pair Code: " + registerDisplayRequest.getPairCode() + " not found.");
        }

        Location location = locationRepository.findById(locationId).orElse(null);
        if (location == null) {
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Location not found for locationId: " + locationId.toString() + ".");
        }

        Display display = new Display();
        display.setLocation(location);
        display.setCompany(user.getCompany());
        display.setName(registerDisplayRequest.getName());
        display.setDescription(registerDisplayRequest.getDescription());
        display.setDeviceId(pairDevice.getDeviceId());
        display.setModel(pairDevice.getModel());
        display.setManufacturer(pairDevice.getManufacturer());
        display.setIsLandscape(pairDevice.getIsLandscape());



        Display dbEntity;
        try {
            dbEntity = displayRepository.save(display);
            pairDevice.setPairedStatus(true);
            pairDevice.setDisplayId(dbEntity.getId());
            pairDevice.setUserId(userId);
            pairDeviceRepository.save(pairDevice);
        } catch (Exception ex) {
            log.error("Error occurred saving entities to db.", ex);
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Error occurred saving entities to db.", ex);
        }

        return dbEntity;
    }
}
