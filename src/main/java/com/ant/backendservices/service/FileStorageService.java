package com.ant.backendservices.service;

import com.ant.backendservices.error.Error;
import com.ant.backendservices.exception.AppException;
import com.ant.backendservices.exception.FileStorageException;
import com.ant.backendservices.repository.FileStorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Slf4j
@Service
public class FileStorageService {

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    private FileStorageRepository fileStorageRepository;

    private String getFileURL(String fileName) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = file.getOriginalFilename();

        try {
            return getFileURL(fileStorageRepository.uploadFile(file));
        } catch (Exception ex) {
            log.error("Could not store file {}. Please try again!", fileName, ex);
            throw new AppException(Error.INTERNAL_SERVER_ERROR, "Could not store file " + fileName + ". Please try again!", ex);
        }
    }

//    public Resource loadFileAsResource(String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if(resource.exists()) {
//                return resource;
//            } else {
//                throw new MyFileNotFoundException("File not found " + fileName);
//            }
//        } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
//        }
//    }
}
