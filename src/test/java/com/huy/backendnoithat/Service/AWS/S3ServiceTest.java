package com.huy.backendnoithat.Service.AWS;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.unit.DataSize;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class S3ServiceTest {
    @Autowired
    S3Service s3Service;
    String currentDir = System.getProperty("user.dir");
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void listObject() {
        ListObjectsResponse listObjectsResponse = s3Service.listObject();
        System.out.println(listObjectsResponse.contents());
        assertFalse(listObjectsResponse.contents().isEmpty());
    }

    @Test
    void putObject() throws IOException {
        File aws = new File(Paths.get(currentDir, "data", "logo", "aws.png").toString());
        File cat = new File(Paths.get(currentDir, "data", "logo", "cat.jpg").toString());
        FileInputStream awsInputImage = new FileInputStream(aws);
        FileInputStream catInputImage = new FileInputStream(cat);

        assertDoesNotThrow(() -> s3Service.putObject(awsInputImage, aws.length(), aws.getName()));
        assertDoesNotThrow(() -> s3Service.putObject(catInputImage, cat.length(), cat.getName()));
        awsInputImage.close();
        catInputImage.close();
    }

    @Test
    void getObject() throws IOException {
        String keyName = UUID.randomUUID().toString();
        File aws = new File(Paths.get(currentDir, "data", "logo", "aws.png").toString());
        FileInputStream awsInputImage = new FileInputStream(aws);
        s3Service.putObject(awsInputImage, aws.length(), keyName);
        ResponseInputStream<GetObjectResponse> responseInputStream = s3Service.getObject(keyName);
        Assertions.assertEquals(responseInputStream.response().contentLength(), aws.length());
        awsInputImage.close();
    }

    @Test
    void multipartUpload() throws IOException {
        String key = UUID.randomUUID().toString();
        long contentLength = DataSize.ofMegabytes(40).toBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        Assertions.assertDoesNotThrow(() -> s3Service.uploadMultipartObject(key, contentLength, inputStream));
    }
}