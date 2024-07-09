package com.huy.backendnoithat.service.general.impl;

import com.huy.backendnoithat.dao.FileStorageDAO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.entity.SavedFileEntity;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.enums.StorageType;
import com.huy.backendnoithat.service.account.AccountService;
import com.huy.backendnoithat.service.aws.S3Service;
import com.huy.backendnoithat.service.general.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStorageServiceImpl implements FileStorageService {
    private final AccountService accountService;
    private final FileStorageDAO fileStorageDAO;
    private final S3Service s3Service;
    @Override
    public void saveNtFile(MultipartFile multipartFile) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.findByUsername(username);
        if (account == null) {
            log.error("Account not found");
            throw new RuntimeException("Account not found");
        }
        String physicalFileName = UUID.randomUUID().toString();
        SavedFileEntity savedFileEntity = SavedFileEntity.builder()
                .fileName(multipartFile.getName())
                .storageType(StorageType.AWS_S3)
                .physicalName(physicalFileName)
                .accountEntity(AccountEntity.builder().id(account.getId()).build())
                .isUploaded(false)
                .build();
        try {
            String key = String.format("%s/%s", username, physicalFileName);
            saveFile(key, multipartFile.getInputStream(), multipartFile.getSize());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveFile(String key, InputStream inputStream, long size) {
        CompletableFuture<PutObjectResponse> future = s3Service.putObjectAsync(inputStream, size, key);
        future.thenAccept(response -> {

        }).exceptionally(exception -> {

            return null;
        });
    }
}
