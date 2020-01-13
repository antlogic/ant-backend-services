package com.ant.backendservices.payload.response;

import lombok.Data;

@Data
public class UploadFileResponse {
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileDownloadUri, String fileType, long size) {
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
