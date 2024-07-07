package com.huy.backendnoithat.Service.AWS;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class S3Service {
    private final S3Client s3Client;
    @Value("${aws.s3.bucket-name}")
    private String bucketName;
    @Value("${aws.s3.multipart-size-MB}")
    private Integer multipartSizeMB;
    public ListObjectsResponse listObject() {
        ListObjectsRequest listObjectRequest = ListObjectsRequest.builder().bucket(bucketName).build();
        return s3Client.listObjects(listObjectRequest);
    }

    public PutObjectResponse putObject(InputStream inputStream, long contentLength, String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("object key can't be null");
        }
        if (contentLength == 0) {
            throw new IllegalArgumentException("contentLength cant be 0");
        }
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream can't be null");
        }
        RequestBody requestBody = RequestBody.fromInputStream(inputStream, contentLength);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .key(key)
                .bucket(bucketName)
                .build();
        return s3Client.putObject(putObjectRequest, requestBody);
    }

    public DeleteObjectResponse deleteObject(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .key(key)
                .bucket(bucketName)
                .build();
        return s3Client.deleteObject(deleteObjectRequest);
    }

    public ResponseInputStream<GetObjectResponse> getObject(String objectKey) {
        if (StringUtils.isEmpty(objectKey)) {
            throw new IllegalArgumentException("object key can't be null");
        }
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        return s3Client.getObject(getObjectRequest);
    }

    public CompleteMultipartUploadResponse uploadMultipartObject(String key, long contentLength, InputStream inputStream) {
        List<CompletedPart> completedParts = new ArrayList<>();
        DataSize partSize = DataSize.ofMegabytes(multipartSizeMB);
        String uploadId = createMultipartUploadRequest(key);
        int partNumber = 1;
        try {
            long remaining = contentLength;
            while (remaining > 0) {
                byte[] partData = inputStream.readNBytes((int) partSize.toBytes());
                UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                        .bucket(bucketName)
                        .uploadId(uploadId)
                        .contentLength(partSize.toBytes())
                        .partNumber(partNumber)
                        .key(key)
                        .build();
                UploadPartResponse uploadPartResponse = s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(partData));

                completedParts.add(CompletedPart.builder()
                        .eTag(uploadPartResponse.eTag())
                        .partNumber(partNumber)
                        .build());

                remaining = remaining - partSize.toBytes();
                partNumber++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
                .parts(completedParts)
                .build();
        CompleteMultipartUploadRequest completeMultipartUploadRequest = CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .uploadId(uploadId)
                .multipartUpload(completedMultipartUpload)
                .key(key)
                .build();
        return s3Client.completeMultipartUpload(completeMultipartUploadRequest);
    }

    private String createMultipartUploadRequest(String key) {
        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .key(key)
                .bucket(bucketName)
                .build();
        CreateMultipartUploadResponse createMultipartUploadResponse = s3Client.createMultipartUpload(createMultipartUploadRequest);
        return createMultipartUploadResponse.uploadId();
    }
}
