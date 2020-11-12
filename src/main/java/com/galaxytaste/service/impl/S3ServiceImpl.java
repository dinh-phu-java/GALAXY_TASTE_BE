package com.galaxytaste.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.galaxytaste.service.S3Services;
import com.galaxytaste.utilities.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class S3ServiceImpl implements S3Services {

    private Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${jsa.s3.bucket}")
    private String bucketName;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${jsa.s3.bucket.url}")
    private String s3BucketUrl;

    @Override
    public void uploadFile(String keyName, String uploadFilePath) {
        try {

            File file = new File(uploadFilePath);
            s3client.putObject(new PutObjectRequest(bucketName, keyName, file));
            logger.info("===================== Upload File - Done! =====================");

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
    }

    @Override
    public void deleteFile(String keyName) {
        logger.info("Deleting file with name= " + keyName);
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, keyName);
        s3client.deleteObject(deleteObjectRequest);
        logger.info("File deleted successfully.");
    }

    @Override
    public String uploadService(MultipartFile fileUpload) throws IOException {
        String fileName= HashUtils.hashFileName(fileUpload.getOriginalFilename());
        InputStream inputStream =fileUpload.getInputStream();
        Path uploadLocalPath= Paths.get(this.uploadPath);
        Path filePath= uploadLocalPath.resolve(fileName);
        Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        this.uploadFile(fileName,filePath.toString());
        Files.delete(filePath);
        return s3BucketUrl+"products/"+fileName;
    }


}
