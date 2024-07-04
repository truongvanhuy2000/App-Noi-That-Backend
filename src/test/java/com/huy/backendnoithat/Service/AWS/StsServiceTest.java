package com.huy.backendnoithat.Service.AWS;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.sts.model.Credentials;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StsServiceTest {
    @Autowired
    StsService stsService;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void basicCredentialGet() {
        Assertions.assertDoesNotThrow(() -> stsService.assumeRole());
    }
}