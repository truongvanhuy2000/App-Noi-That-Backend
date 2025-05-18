package com.huy.backendnoithat.service.file;

import com.huy.backendnoithat.dao.v1.AccountEntityDAO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.model.FileSearchRequest;
import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.PaginationResponse;
import com.huy.backendnoithat.model.UploadFile;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.enums.FileType;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class GoogleDriveFileStorageServiceTest {
    @Autowired
    private GoogleDriveFileStorageService googleDriveFileStorageService;
    @Autowired
    private AccountEntityDAO accountEntityDAO;

    @PostConstruct
    void init() {
        if (accountEntityDAO.findById(1).isEmpty()) {
            accountEntityDAO.save(AccountEntity.builder().id(1).username("admin").password("password").build());
        }
    }

    @Test
    void updateFileInfo() {
    }

    @Test
    void deleteFile() {
    }

    @Test
    void getAllFileOfUser() {
    }

    @Test
    void getFileInfo() {
    }

    @Test
    void getFile() {
    }

    @Test
    void updateFile() {
    }

    @Test
    void saveFile() {
        Long userID = 1L;
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(userID, "admin"));

        int sizeInMB = 5;
        byte[] largeBytes = new byte[sizeInMB * 1024];
        new Random().nextBytes(largeBytes);
        InputStream inputStream = new ByteArrayInputStream(largeBytes);
        // Create UploadFile object
        var uploadFile = UploadFile.builder()
            .size(largeBytes.length)
            .fileName("test.txt" + UUID.randomUUID())
            .contentType("text/plain")
            .inputStream(inputStream)
            .fileType(FileType.NT_FILE)
            .build();

        googleDriveFileStorageService.saveFile(FileType.NT_FILE, uploadFile);
    }

    @Test
    void find() {
        long userID = 1L;
        var paginationRequest = PaginationRequest.builder()
            .page(0)
            .size(10)
            .build();
        var fileSearchRequest = FileSearchRequest.builder().userId(Math.toIntExact(userID)).build();
        PaginationResponse<List<SavedFileDTO>> response = googleDriveFileStorageService.find(paginationRequest, fileSearchRequest);
        assertNotNull(response);
    }
}