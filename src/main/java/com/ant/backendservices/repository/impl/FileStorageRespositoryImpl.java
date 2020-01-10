package com.ant.backendservices.repository.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.ant.backendservices.repository.FileStorageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class FileStorageRespositoryImpl implements FileStorageRepository {

    @Value("${app.awsServices.bucketName}")
    String bucketName;

    @Value("${cloud.aws.region.static}")
    String region;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Override
    public String getBucketName() {
        return bucketName;
    }

    @Override
    public String getRegion() {
        return region;
    }

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        File file = convertMultiPartFileToFile(multipartFile);
        String uniqueFileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        uploadFileToS3bucket(uniqueFileName, file, bucketName);
        return uniqueFileName;
    }

    @Override
    public S3Object downloadFile(String fileName) {
        Optional<S3Object> object = Optional.empty();
        if (!StringUtils.isEmpty(fileName)) {
            object = Optional.of(downloadFileFromS3bucket(fileName, bucketName));
        }
        return object.orElse(null);
    }

    private void uploadFileToS3bucket(String fileName, File file, String bucketName) {
        PutObjectResult result = amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, file));

    }

    private S3Object downloadFileFromS3bucket(String fileName, String bucketName) {
        S3Object object = amazonS3Client.getObject(bucketName,  fileName);
        return object;

    }

    private void deleteFileFromS3bucket(String fileName, String bucketName) {
        amazonS3Client.deleteObject(bucketName, fileName);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
