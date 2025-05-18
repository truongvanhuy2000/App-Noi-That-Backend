package com.huy.backendnoithat.service.file;

import com.huy.backendnoithat.model.FileSearchRequest;
import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.PaginationResponse;
import com.huy.backendnoithat.model.UploadFile;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.enums.FileType;
import jakarta.servlet.ServletOutputStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;

public interface FileStorageService {
    @Deprecated
    default SavedFileDTO saveFile(UploadFile uploadFile) {
        throw new UnsupportedOperationException("No longer supported");
    }

    @Deprecated
    default SavedFileDTO getFile(int fileID, OutputStream outputStream) {
        throw new UnsupportedOperationException("No longer supported");
    }

    @Deprecated
    default SavedFileDTO updateFile(UploadFile uploadFile, int fileID) {
        throw new UnsupportedOperationException("No longer supported");
    }

    @Deprecated
    default void deleteFile(int fileID) {
        throw new UnsupportedOperationException("No longer supported");
    }

    @Deprecated
    default List<SavedFileDTO> getAllFileOfUser() {
        throw new UnsupportedOperationException("No longer supported");
    }

    @Deprecated
    default SavedFileDTO updateFileInfo(int fileID, SavedFileDTO savedFileDTO) {
        throw new UnsupportedOperationException("No longer supported");
    }

    @Deprecated
    default SavedFileDTO getFileInfo(int fileID) {
        throw new UnsupportedOperationException("No longer supported");
    }

    @Deprecated
    default SavedFileDTO saveNtFile(MultipartFile multipartFile) {
        throw new UnsupportedOperationException("No longer supported");
    }

    @Deprecated
    default SavedFileDTO getNtFile(int fileID, ServletOutputStream outputStream) {
        throw new UnsupportedOperationException("No longer supported");
    }

    @Deprecated
    default void updateNtFile(MultipartFile multipartFile, int fileID) {
        throw new UnsupportedOperationException("No longer supported");
    }

    void updateFileInfo(int fileID, FileType fileType, SavedFileDTO savedFileDTO);

    void deleteFile(int fileID, FileType fileType);

    List<SavedFileDTO> getAllFileOfUser(FileType fileType);

    SavedFileDTO getFileInfo(int fileID, FileType fileType);

    SavedFileDTO getFile(int fileID, FileType fileType, OutputStream outputStream);

    void updateFile(UploadFile uploadFile, FileType fileType, int fileID);

    SavedFileDTO saveFile(FileType fileType, UploadFile uploadFile);

    PaginationResponse<List<SavedFileDTO>> find(PaginationRequest paginationRequest, FileSearchRequest fileSearchRequest);
}
