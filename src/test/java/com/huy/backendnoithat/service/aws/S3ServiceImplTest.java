package com.huy.backendnoithat.service.aws;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.unit.DataSize;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class S3ServiceImplTest {
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
    void putObject() {
        String key = UUID.randomUUID().toString();
        long contentLength = DataSize.ofMegabytes(10).toBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);

        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        assertDoesNotThrow(() -> s3Service.putObject(inputStream, contentLength, key));
    }

    @Test
    void getObject() {
        String key = UUID.randomUUID().toString();
        long contentLength = DataSize.ofMegabytes(10).toBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());

        s3Service.putObject(inputStream, contentLength, key);
        ResponseInputStream<GetObjectResponse> responseInputStream = s3Service.getObject(key);

        Assertions.assertEquals(responseInputStream.response().contentLength(), contentLength);
    }

    @Test
    void multipartUpload() {
        String key = UUID.randomUUID().toString();
        long contentLength = DataSize.ofMegabytes(40).toBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        Assertions.assertDoesNotThrow(() -> s3Service.uploadMultipartObject(key, contentLength, inputStream));
    }

    @Test
    void multipartUploadAync() throws ExecutionException, InterruptedException {
        String key = UUID.randomUUID().toString();
        long contentLength = DataSize.ofMegabytes(10).toBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        var completeFuture = s3Service.putObjectAsync(inputStream, contentLength, key);
        completeFuture.get();

        Assertions.assertDoesNotThrow(() -> s3Service.getObject(key));
    }

    @Test
    void multipartUploadAyncWithFolder() throws ExecutionException, InterruptedException {
        String key = String.format("%s/%s", "test", UUID.randomUUID());
        long contentLength = DataSize.ofMegabytes(10).toBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) contentLength);
        InputStream inputStream = new ByteArrayInputStream(byteBuffer.array());
        var completeFuture = s3Service.putObjectAsync(inputStream, contentLength, key);
        completeFuture.get();

        Assertions.assertDoesNotThrow(() -> s3Service.getObject(key));
    }
}