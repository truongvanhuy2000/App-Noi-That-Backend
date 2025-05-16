package com.huy.backendnoithat.service.file;

import com.huy.backendnoithat.model.UploadFile;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.List;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoogleDriveFileStorageService implements FileStorageService {


    @Override
    public SavedFileDTO saveFile(UploadFile uploadFile) {

        return null;
    }

    @Override
    public SavedFileDTO getFile(int fileID, OutputStream outputStream) {
        return null;
    }

    @Override
    public SavedFileDTO updateFile(UploadFile uploadFile, int fileID) {
        return null;
    }

    @Override
    public void deleteFile(int fileID) {

    }

    @Override
    public void updateNtFile(MultipartFile multipartFile, int fileID) {

    }

    @Override
    public List<SavedFileDTO> getAllFileOfUser() {
        return List.of();
    }

    @Override
    public SavedFileDTO updateFileInfo(int fileID, SavedFileDTO savedFileDTO) {

    }

    @Override
    public SavedFileDTO getFileInfo(int fileID) {
        return null;
    }
}
