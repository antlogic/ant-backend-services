package com.ant.backendservices.service;

import com.ant.backendservices.exception.FileStorageException;
import com.ant.backendservices.repository.FileStorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Slf4j
@Service
public class FileStorageService {

    @Autowired
    private FileStorageRepository fileStorageRepository;

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = file.getOriginalFilename();

        try {
            return createBucketFileURL(fileStorageRepository.uploadFile(file));
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private String createBucketFileURL(String fileName) {
        return "https://" + fileStorageRepository.getBucketName() + ".s3." + fileStorageRepository.getRegion() + ".amazonaws.com/" + fileName;
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
