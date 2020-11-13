package com.galaxytaste.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Services {
    void uploadFile(String keyName, String uploadFilePath);
    String uploadService(MultipartFile fileUpload) throws IOException;
    void deleteFile(final String keyName);

}
