//package com.huy.backendnoithat.service.aws;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class StsServiceTest {
//    @Autowired
//    StsService stsService;
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void basicCredentialGet() {
//        Assertions.assertDoesNotThrow(() -> stsService.assumeS3AccessRole());
//    }
//}