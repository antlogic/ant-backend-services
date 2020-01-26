package com.ant.backendservices.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StorageBucketUtils {

    @Value("${app.awsServices.bucketName}")
    private String bucketName;

    @Value("${cloud.aws.region.static}")
    private String region;

    public String getFileURL(String fileName) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
    }
}
