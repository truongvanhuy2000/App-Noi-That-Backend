//package com.huy.backendnoithat.service.file;
//
//import com.huy.backendnoithat.dao.FileStorageDAO;
//import com.huy.backendnoithat.entity.Account.AccountEntity;
//import com.huy.backendnoithat.entity.SavedFileEntity;
//import com.huy.backendnoithat.exception.FileStorageException;
//import com.huy.backendnoithat.exception.errorCode.FileStorageErrorCode;
//import com.huy.backendnoithat.model.UploadFile;
//import com.huy.backendnoithat.model.dto.AccountManagement.Account;
//import com.huy.backendnoithat.model.dto.SavedFileDTO;
//import com.huy.backendnoithat.model.enums.FileType;
//import com.huy.backendnoithat.model.enums.StorageType;
//import com.huy.backendnoithat.service.v0.account.AccountService;
//import com.huy.backendnoithat.service.aws.S3Service;
//import jakarta.servlet.ServletOutputStream;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.core.ResponseInputStream;
//import software.amazon.awssdk.services.s3.model.GetObjectResponse;
//import software.amazon.awssdk.services.s3.model.PutObjectResponse;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.concurrent.CompletableFuture;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//public class S3FileStorageService implements FileStorageService {
//    private final FileStorageDAO fileStorageDAO;
//    private final S3Service s3Service;
//    private final ModelMapper modelMapper;
//    private final AccountService accountService;
//
//    @Override
//    public SavedFileDTO saveNtFile(MultipartFile multipartFile) {
//        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Long userID)) {
//            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
//        }
//        Account account = accountService.findById(userID.intValue());
//        String physicalFileName = UUID.randomUUID().toString();
//        SavedFileEntity savedFileEntity = SavedFileEntity.builder()
//                .fileName(multipartFile.getOriginalFilename())
//                .storageType(StorageType.AWS_S3)
//                .physicalName(physicalFileName)
//                .accountEntity(AccountEntity.builder().id(account.getId()).build())
//                .isUploaded(false)
//                .isBackup(false)
//                .build();
//
//        SavedFileEntity saved = fileStorageDAO.save(savedFileEntity);
//        try {
//            String key = String.format("%s/%s/%s", account.getUsername(), "nt", physicalFileName);
//            saveFile(key, multipartFile.getInputStream(), multipartFile.getSize(), saved);
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//            throw new FileStorageException(FileStorageErrorCode.CANT_TRANSFER);
//        }
//        return modelMapper.map(saved, SavedFileDTO.class);
//    }
//
//    @Override
//    public SavedFileDTO saveFile(UploadFile uploadFile) {
//        return null;
//    }
//
//    @Override
//    public SavedFileDTO getFile(int fileID, OutputStream outputStream) {
//        return null;
//    }
//
//    @Override
//    public SavedFileDTO updateFile(UploadFile uploadFile, int fileID) {
//        return null;
//    }
//
//    @Override
//    public SavedFileDTO getNtFile(int fileID, ServletOutputStream outputStream) {
//        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Long userID)) {
//            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
//        }
//        Account account = accountService.findById(userID.intValue());
//        SavedFileEntity savedFile = fileStorageDAO.findByIdAndAccountId(fileID, account.getId());
//        if (savedFile == null) {
//            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
//        }
//        if (savedFile.isUploaded()) {
//            log.info("File is store on S3");
//            String key = String.format("%s/nt/%s", account.getUsername(), savedFile.getPhysicalName());
//            ResponseInputStream<GetObjectResponse> responseInputStream = s3Service.getObject(key);
//            try {
//                long transferredByte = responseInputStream.transferTo(outputStream);
//                if (transferredByte != responseInputStream.response().contentLength()) {
//                    log.error("Not matching content length");
//                    throw new FileStorageException(FileStorageErrorCode.CANT_TRANSFER);
//                }
//            } catch (IOException e) {
//                log.error("Failed to transfer to output stream");
//                throw new FileStorageException(FileStorageErrorCode.CANT_TRANSFER);
//            }
//        } else if (savedFile.isBackup()) {
//            log.info("File is store locally");
//            // TODO Handle when file is saved on the disk because of upload failure
//        } else {
//            log.error("File is neither store on S3 or locally");
//            throw new FileStorageException(FileStorageErrorCode.FILE_NOT_FOUND);
//        }
//        return modelMapper.map(savedFile, SavedFileDTO.class);
//    }
//
//    @Override
//    public void deleteFile(int fileID) {
//        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Long userID)) {
//            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
//        }
//        Account account = accountService.findById(userID.intValue());
//        SavedFileEntity savedFile = fileStorageDAO.findByIdAndAccountId(fileID, account.getId());
//        if (savedFile == null) {
//            log.error("This file not belong to this account: {} or it's not exist", account.getUsername());
//            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
//        }
//        String key = String.format("%s/%s/%s", account.getUsername(), "nt", savedFile.getPhysicalName());
//        s3Service.deleteObject(key);
//        fileStorageDAO.delete(savedFile);
//    }
//
//    @Override
//    public void updateNtFile(MultipartFile multipartFile, int fileID) {
//        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Long userID)) {
//            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
//        }
//        Account account = accountService.findById(userID.intValue());
//        SavedFileEntity savedFile = fileStorageDAO.findByIdAndAccountId(fileID, account.getId());
//        if (savedFile == null) {
//            log.error("This file not belong to this account: {} or it's not exist", account.getUsername());
//            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
//        }
//        try {
//            String key = String.format("%s/%s/%s", account.getUsername(), "nt", savedFile.getPhysicalName());
//            saveFile(key, multipartFile.getInputStream(), multipartFile.getSize(), savedFile);
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//            throw new FileStorageException(FileStorageErrorCode.CANT_TRANSFER);
//        }
//    }
//
//    @Override
//    public void updateFileInfo(int fileID, FileType fileType, SavedFileDTO savedFileDTO) {
//
//    }
//
//    @Override
//    public void deleteFile(int fileID, FileType fileType) {
//
//    }
//
//    @Override
//    public List<SavedFileDTO> getAllFileOfUser(FileType fileType) {
//        return List.of();
//    }
//
//    @Override
//    public SavedFileDTO getFileInfo(int fileID, FileType fileType) {
//        return null;
//    }
//
//    @Override
//    public SavedFileDTO getFile(int fileID, FileType fileType, OutputStream outputStream) {
//        return null;
//    }
//
//    @Override
//    public SavedFileDTO getFile(int fileID, FileType fileType, ServletOutputStream outputStream) {
//        return null;
//    }
//
//    @Override
//    public void updateFile(UploadFile uploadFile, FileType fileType, int fileID) {
//
//    }
//
//    @Override
//    public SavedFileDTO saveFile(FileType fileType, UploadFile uploadFile) {
//        return null;
//    }
//
//    @Override
//    public List<SavedFileDTO> getAllFileOfUser() {
//        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Long userID)) {
//            throw new FileStorageException(FileStorageErrorCode.CANT_ACCESS_FILE);
//        }
//        List<SavedFileEntity> savedFileEntities = fileStorageDAO.findAllByAccountId(userID.intValue());
//        return savedFileEntities.stream().map(item -> modelMapper.map(item, SavedFileDTO.class)).toList();
//    }
//
//    @Override
//    public SavedFileDTO updateFileInfo(int fileID, SavedFileDTO savedFileDTO) {
//        Optional<SavedFileEntity> optional = fileStorageDAO.findById(fileID);
//        if (optional.isEmpty()) {
//            throw new FileStorageException(FileStorageErrorCode.FILE_NOT_FOUND);
//        }
//        SavedFileEntity savedFile = optional.get();
//        savedFile.setFileName(savedFileDTO.getFileName());
//        fileStorageDAO.save(savedFile);
//        return null;
//    }
//
//    @Override
//    public SavedFileDTO getFileInfo(int fileID) {
//        Optional<SavedFileEntity> optional = fileStorageDAO.findById(fileID);
//        if (optional.isEmpty()) {
//            throw new FileStorageException(FileStorageErrorCode.FILE_NOT_FOUND);
//        }
//        SavedFileEntity savedFile = optional.get();
//        return modelMapper.map(savedFile, SavedFileDTO.class);
//    }
//
//    private void saveFile(String key, InputStream inputStream, long size, SavedFileEntity savedEntity) {
//        CompletableFuture<PutObjectResponse> future = s3Service.putObjectAsync(inputStream, size, key);
//        future.thenAccept(response -> {
//            log.info("Saved file {} with etag {}", key, response.eTag());
//            fileStorageDAO.save(savedEntity.toBuilder().isUploaded(true).build());
//        }).exceptionally(exception -> {
//            log.error(exception.getMessage(), exception);
//            backupFileToDisk();
//            fileStorageDAO.save(savedEntity.toBuilder().isUploaded(false).isBackup(true).storageType(StorageType.LOCAL).build());
//            return null;
//        });
//    }
//
//    private void backupFileToDisk() {
//        // TODO: implement backup to disk when upload to s3 failed
//    }
//
//    private void getBackupFile() {
//        // TODO: implement get backed up file
//    }
//}
