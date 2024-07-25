package com.huy.backendnoithat.service.aws;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

public interface S3Service {
    ListObjectsResponse listObject();

    PutObjectResponse putObject(InputStream inputStream, long contentLength, String key);

    CompletableFuture<PutObjectResponse> putObjectAsync(InputStream inputStream, long contentLength, String key);

    DeleteObjectResponse deleteObject(String key);

    ResponseInputStream<GetObjectResponse> getObject(String objectKey);

    CompleteMultipartUploadResponse uploadMultipartObject(String key, long contentLength, InputStream inputStream);
}
