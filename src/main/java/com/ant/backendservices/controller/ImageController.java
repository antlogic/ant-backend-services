package com.ant.backendservices.controller;

import com.ant.backendservices.model.Image;
import com.ant.backendservices.payload.response.RegisterResponse;
import com.ant.backendservices.payload.response.RetrieveImagesResponse;
import com.ant.backendservices.service.AuthService;
import com.ant.backendservices.service.ImageService;
import com.ant.backendservices.transformer.ImageTransformer;
import com.ant.backendservices.utils.StorageBucketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/images")
public class ImageController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageTransformer imageTransformer;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveImagesResponse> uploadImage(@RequestParam("file")MultipartFile file) {
        Long companyId = authService.getLoggedInCompanyId();
        imageService.uploadImage(file, companyId);
        return getImages();
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RetrieveImagesResponse> getImages() {
        Long companyId = authService.getLoggedInCompanyId();
        List<Image> images = imageService.getImagesByCompanyId(companyId);
        RetrieveImagesResponse response = imageTransformer.imageEntityListToRetrieveImagesResponse(images);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
