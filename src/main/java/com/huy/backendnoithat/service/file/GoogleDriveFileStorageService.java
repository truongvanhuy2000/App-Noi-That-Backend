package com.huy.backendnoithat.service.file;

import com.blazebit.persistence.PagedList;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.huy.backendnoithat.dao.FileStorageDAO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.entity.SavedFileEntity;
import com.huy.backendnoithat.manager.SavedFileEntityManager;
import com.huy.backendnoithat.mapper.SavedFileEntityDTOMapper;
import com.huy.backendnoithat.model.FileSearchRequest;
import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.PaginationResponse;
import com.huy.backendnoithat.model.UploadFile;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.model.enums.StorageType;
import com.huy.backendnoithat.model.enums.UploadStatus;
import com.huy.backendnoithat.utils.SecurityUtils;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoogleDriveFileStorageService implements FileStorageService {
    private final Drive googleDrive;
    private final FileStorageDAO fileStorageDAO;
    private final SavedFileEntityManager savedFileEntityManager;
    private final SavedFileEntityDTOMapper savedFileEntityDTOMapper;

    @Override
    public void updateFileInfo(int fileID, FileType fileType, SavedFileDTO savedFileDTO) {
        SavedFileEntity entity = fileStorageDAO.findById(fileID).orElseThrow();
        if (entity.getFileType() != fileType) {
            throw new IllegalArgumentException("File type mismatch");
        }
        entity.setFileName(savedFileDTO.getFileName());
        fileStorageDAO.save(entity);
        try {
            File file = new File();
            file.setName(savedFileDTO.getFileName());
            googleDrive.files().update(entity.getPhysicalName(), file).execute();
        } catch (IOException e) {
            throw new RuntimeException("Failed to update file on Google Drive", e);
        }
    }

    @Override
    public void deleteFile(int fileID, FileType fileType) {
        SavedFileEntity entity = fileStorageDAO.findById(fileID).orElseThrow();
        if (entity.getFileType() != fileType) {
            throw new IllegalArgumentException("File type mismatch");
        }
        try {
            googleDrive.files().delete(entity.getPhysicalName()).execute();
            fileStorageDAO.delete(entity);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file from Google Drive", e);
        }
    }

    @Override
    public List<SavedFileDTO> getAllFileOfUser(FileType fileType) {
        // Example for current user, modify as needed
        long userID = SecurityUtils.getUserFromContext();
        List<SavedFileEntity> entities = fileStorageDAO.findAllByAccountIdAndFileType(userID, fileType);
        return entities.stream().map(savedFileEntityDTOMapper::toDTO).toList();
    }

    @Override
    public SavedFileDTO getFileInfo(int fileID, FileType fileType) {
        SavedFileEntity entity = fileStorageDAO.findById(fileID).orElseThrow();
        if (entity.getFileType() != fileType) {
            throw new IllegalArgumentException("File type mismatch");
        }
        return savedFileEntityDTOMapper.toDTO(entity);
    }

    @Override
    public SavedFileDTO getFile(int fileID, FileType fileType, OutputStream outputStream) {
        SavedFileEntity entity = fileStorageDAO.findById(fileID).orElseThrow();
        if (entity.getFileType() != fileType) {
            throw new IllegalArgumentException("File type mismatch");
        }
        try {
            googleDrive.files().get(entity.getPhysicalName()).executeMediaAndDownloadTo(outputStream);
            return savedFileEntityDTOMapper.toDTO(entity);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file from Google Drive", e);
        }
    }

    @Override
    public void updateFile(UploadFile uploadFile, FileType fileType, int fileID) {
        SavedFileEntity entity = fileStorageDAO.findById(fileID).orElseThrow();
        if (entity.getFileType() != fileType) {
            throw new IllegalArgumentException("File type mismatch");
        }

        try {
            File fileMeta = new File();
            fileMeta.setName(uploadFile.getFileName());
            googleDrive.files()
                .update(entity.getPhysicalName(), fileMeta,
                    new InputStreamContent(uploadFile.getContentType(), uploadFile.getInputStream()))
                .execute();
            // Update metadata in DB if needed
            entity.setFileName(uploadFile.getFileName());
            fileStorageDAO.save(entity);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update file on Google Drive", e);
        }
    }

    @Override
    public SavedFileDTO saveFile(FileType fileType, UploadFile uploadFile) {
        long userID = SecurityUtils.getUserFromContext();
        AccountEntity account = AccountEntity.builder().id(Math.toIntExact(userID)).build();
        try {
            File fileMeta = new File();
            fileMeta.setName(uploadFile.getFileName())
                .setParents(List.of("1HlLY0UpqW02kt31GYAVI-V4WPqyLUPjS"));

            File uploadedFile = googleDrive.files()
                .create(fileMeta, new InputStreamContent(uploadFile.getContentType(), uploadFile.getInputStream()))
                .setFields("id, name, parents")
                .execute();

            SavedFileEntity entity = new SavedFileEntity();
            entity.setFileName(uploadFile.getFileName());
            entity.setPhysicalName(uploadedFile.getId());
            entity.setFileType(fileType);
            entity.setSize(uploadFile.getSize());
            entity.setUploadStatus(UploadStatus.UPLOADED);
            entity.setStorageType(StorageType.GOOGLE_DRIVE);
            entity.setAccountEntity(account); // or however you get the user
            fileStorageDAO.save(entity);

            return savedFileEntityDTOMapper.toDTO(entity);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Google Drive", e);
        }
    }

    @Override
    public PaginationResponse<List<SavedFileDTO>> find(
        PaginationRequest paginationRequest, FileSearchRequest fileSearchRequest
    ) {
        PagedList<SavedFileEntity> entities = savedFileEntityManager
            .search(paginationRequest.getPage(), paginationRequest.getSize(), fileSearchRequest);
        List<SavedFileDTO> savedFileDTOList = entities.stream()
            .map(savedFileEntityDTOMapper::toDTO)
            .toList();
        return PaginationResponse.<List<SavedFileDTO>>builder()
            .total(entities.getTotalSize())
            .data(savedFileDTOList)
            .build();
    }
}
