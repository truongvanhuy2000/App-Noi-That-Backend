package com.huy.backendnoithat.service.general.impl;

import com.huy.backendnoithat.dao.FileStorageDAO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.entity.SavedFileEntity;
import com.huy.backendnoithat.exception.FileStorageException;
import com.huy.backendnoithat.exception.errorCode.FileStorageErrorCode;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.enums.StorageType;
import com.huy.backendnoithat.service.account.AccountService;
import com.huy.backendnoithat.service.aws.S3Service;
import com.huy.backendnoithat.service.general.FileStorageService;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStorageServiceImpl implements FileStorageService {
    private final FileStorageDAO fileStorageDAO;
    private final S3Service s3Service;
    private final ModelMapper modelMapper;

    @Override
    public SavedFileDTO saveNtFile(MultipartFile multipartFile) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String physicalFileName = UUID.randomUUID().toString();
        SavedFileEntity savedFileEntity = SavedFileEntity.builder()
                .fileName(multipartFile.getOriginalFilename())
                .storageType(StorageType.AWS_S3)
                .physicalName(physicalFileName)
                .accountEntity(AccountEntity.builder().id(account.getId()).build())
                .isUploaded(false)
                .isBackup(false)
                .build();

        SavedFileEntity saved = fileStorageDAO.save(savedFileEntity);
        try {
            String key = String.format("%s/%s/%s", account.getUsername(), "nt", physicalFileName);
            saveFile(key, multipartFile.getInputStream(), multipartFile.getSize(), saved);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new FileStorageException(FileStorageErrorCode.CANT_TRANSFER);
        }
        return modelMapper.map(saved, SavedFileDTO.class);
    }

    @Override
    public SavedFileDTO getNtFile(int fileID, ServletOutputStream outputStream) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SavedFileEntity savedFile = fileStorageDAO.findByIdAndAccountId(fileID, account.getId());
        if (savedFile == null) {
            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
        }
        if (savedFile.isUploaded()) {
            log.info("File is store on S3");
            String key = String.format("%s/nt/%s", account.getUsername(), savedFile.getPhysicalName());
            ResponseInputStream<GetObjectResponse> responseInputStream = s3Service.getObject(key);
            try {
                long transferredByte = responseInputStream.transferTo(outputStream);
                if (transferredByte != responseInputStream.response().contentLength()) {
                    log.error("Not matching content length");
                    throw new FileStorageException(FileStorageErrorCode.CANT_TRANSFER);
                }
            } catch (IOException e) {
                log.error("Failed to transfer to output stream");
                throw new FileStorageException(FileStorageErrorCode.CANT_TRANSFER);
            }
        } else if (savedFile.isBackup()) {
            log.info("File is store locally");
            // TODO Handle when file is saved on the disk because of upload failure
        } else {
            log.error("File is neither store on S3 or locally");
            throw new FileStorageException(FileStorageErrorCode.FILE_NOT_FOUND);
        }
        return modelMapper.map(savedFile, SavedFileDTO.class);
    }

    @Override
    public void deleteNtFile(int fileID) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SavedFileEntity savedFile = fileStorageDAO.findByIdAndAccountId(fileID, account.getId());
        if (savedFile == null) {
            log.error("This file not belong to this account: {} or it's not exist", account.getUsername());
            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
        }
        String key = String.format("%s/%s/%s", account.getUsername(), "nt", savedFile.getPhysicalName());
        s3Service.deleteObject(key);
    }

    @Override
    public void updateNtFile(MultipartFile multipartFile, int fileID) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SavedFileEntity savedFile = fileStorageDAO.findByIdAndAccountId(fileID, account.getId());
        if (savedFile == null) {
            log.error("This file not belong to this account: {} or it's not exist", account.getUsername());
            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
        }
        try {
            String key = String.format("%s/%s/%s", account.getUsername(), "nt", savedFile.getPhysicalName());
            saveFile(key, multipartFile.getInputStream(), multipartFile.getSize(), savedFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new FileStorageException(FileStorageErrorCode.CANT_TRANSFER);
        }
    }

    @Override
    public List<SavedFileDTO> getAllFileOfUser() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SavedFileEntity> savedFileEntities = fileStorageDAO.findAllByAccountId(account.getId());
        return savedFileEntities.stream().map(item -> modelMapper.map(item, SavedFileDTO.class)).toList();
    }

    @Override
    public void updateFileInfo(int fileID, SavedFileDTO savedFileDTO) {
        Optional<SavedFileEntity> optional = fileStorageDAO.findById(fileID);
        if (optional.isEmpty()) {
            throw new FileStorageException(FileStorageErrorCode.FILE_NOT_FOUND);
        }
        SavedFileEntity savedFile = optional.get();
        savedFile.setFileName(savedFileDTO.getFileName());
        fileStorageDAO.save(savedFile);
    }

    @Override
    public SavedFileDTO getFileInfo(int fileID) {
        Optional<SavedFileEntity> optional = fileStorageDAO.findById(fileID);
        if (optional.isEmpty()) {
            throw new FileStorageException(FileStorageErrorCode.FILE_NOT_FOUND);
        }
        SavedFileEntity savedFile = optional.get();
        return modelMapper.map(savedFile, SavedFileDTO.class);
    }

    private void saveFile(String key, InputStream inputStream, long size, SavedFileEntity savedEntity) {
        CompletableFuture<PutObjectResponse> future = s3Service.putObjectAsync(inputStream, size, key);
        future.thenAccept(response -> {
            log.info("Saved file {} with etag {}", key, response.eTag());
            fileStorageDAO.save(savedEntity.toBuilder().isUploaded(true).build());
        }).exceptionally(exception -> {
            log.error(exception.getMessage(), exception);
            backupFileToDisk();
            fileStorageDAO.save(savedEntity.toBuilder().isUploaded(false).isBackup(true).storageType(StorageType.LOCAL).build());
            return null;
        });
    }

    private void backupFileToDisk() {
        // TODO: implement backup to disk when upload to s3 failed
    }

    private void getBackupFile() {
        // TODO: implement get backed up file
    }
}
