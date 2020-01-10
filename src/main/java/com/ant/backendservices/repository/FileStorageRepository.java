package com.ant.backendservices.repository;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface FileStorageRepository {
    String getBucketName();
    String getRegion();
    String uploadFile(MultipartFile multipartFile);
    S3Object downloadFile(String fileName);
}
