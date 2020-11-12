package com.galaxytaste.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Services {
    void uploadFile(String keyName, String uploadFilePath);

    void deleteFile(final String keyName);

    String uploadService(MultipartFile fileUpload) throws IOException;
}
