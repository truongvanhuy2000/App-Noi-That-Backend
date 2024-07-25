package com.huy.backendnoithat.service.general;

import com.huy.backendnoithat.model.dto.SavedFileDTO;
import jakarta.servlet.ServletOutputStream;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {

    SavedFileDTO saveNtFile(MultipartFile multipartFile);

    SavedFileDTO getNtFile(int fileID, ServletOutputStream outputStream);

    void deleteNtFile(int fileID);

    void updateNtFile(MultipartFile multipartFile, int fileID);

    List<SavedFileDTO> getAllFileOfUser();

    void updateFileInfo(int fileID, SavedFileDTO savedFileDTO);

    SavedFileDTO getFileInfo(int fileID);
}
