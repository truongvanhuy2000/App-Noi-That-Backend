package com.huy.backendnoithat.service.file;

import com.huy.backendnoithat.model.UploadFile;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import jakarta.servlet.ServletOutputStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;

public interface FileStorageService {

    @Deprecated
    default SavedFileDTO saveNtFile(MultipartFile multipartFile) {
        throw new UnsupportedOperationException("No longer supported");
    };

    SavedFileDTO saveFile(UploadFile uploadFile);
    SavedFileDTO getFile(int fileID, OutputStream outputStream);
    SavedFileDTO updateFile(UploadFile uploadFile, int fileID);

    @Deprecated
    default SavedFileDTO getNtFile(int fileID, ServletOutputStream outputStream) {
        throw new UnsupportedOperationException("No longer supported");
    };

    void deleteFile(int fileID);

    void updateNtFile(MultipartFile multipartFile, int fileID);

    List<SavedFileDTO> getAllFileOfUser();

    SavedFileDTO updateFileInfo(int fileID, SavedFileDTO savedFileDTO);

    SavedFileDTO getFileInfo(int fileID);
}
