package com.huy.backendnoithat.controller;

import com.huy.backendnoithat.model.*;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.service.file.FileStorageService;
import com.huy.backendnoithat.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/file-storage")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStorageController {
    private final FileStorageService fileStorageService;

    @PostMapping(value = "/{file-type}/upload", consumes = "multipart/form-data")
    public SavedFileDTO saveFile(
        @RequestParam("file") MultipartFile multipartFile,
        @PathVariable("file-type") FileType fileType
    ) throws IOException {
        var uploadFile = UploadFile.builder()
            .contentType(multipartFile.getContentType())
            .fileName(multipartFile.getOriginalFilename())
            .size(multipartFile.getSize())
            .inputStream(multipartFile.getInputStream())
            .build();
        return fileStorageService.saveFile(fileType, uploadFile);
    }

    @PutMapping(value = "/{file-type}/{file-id}/upload", consumes = "multipart/form-data")
    public void updateFile(
        @RequestParam("file") MultipartFile multipartFile,
        @PathVariable("file-id") int fileID,
        @PathVariable("file-type") FileType fileType
    ) throws IOException {
        var uploadFile = UploadFile.builder()
            .contentType(multipartFile.getContentType())
            .fileName(multipartFile.getOriginalFilename())
            .size(multipartFile.getSize())
            .inputStream(multipartFile.getInputStream())
            .build();
        fileStorageService.updateFile(uploadFile, fileType, fileID);
    }

    @GetMapping("/{file-type}/{file-id}/download")
    public void downloadFile(
        HttpServletResponse httpResponse,
        @PathVariable("file-id") int fileID,
        @PathVariable("file-type") FileType fileType
    ) throws IOException {
        SavedFileDTO savedFileDTO = fileStorageService.getFile(fileID, fileType, httpResponse.getOutputStream());
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
            .filename(savedFileDTO.getFileName())
            .build();
        httpResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
    }

    @GetMapping("/{file-type}/{file-id}/file-info")
    public SavedFileDTO getFileInfo(
        @PathVariable("file-id") int fileID,
        @PathVariable("file-type") FileType fileType
    ) {
        return fileStorageService.getFileInfo(fileID, fileType);
    }

    @GetMapping("/{file-type}/find")
    public PaginationResponse<List<SavedFileDTO>> find(
        @ModelAttribute PaginationRequest paginationRequest,
        @ModelAttribute FileSearchRequest fileSearchRequest
    ) {
        long userID = SecurityUtils.getUserFromContext();
        fileSearchRequest.setUserId(Math.toIntExact(userID));
        return fileStorageService.find(paginationRequest, fileSearchRequest);
    }

    @GetMapping("/{file-type}/all")
    public List<SavedFileDTO> getAllFileOfUser(
        @PathVariable("file-type") FileType fileType
    ) {
        return fileStorageService.getAllFileOfUser(fileType);
    }

    @DeleteMapping("/{file-type}/{file-id}")
    public void deleteFile(
        @PathVariable("file-id") int fileID,
        @PathVariable("file-type") FileType fileType
    ) {
        fileStorageService.deleteFile(fileID, fileType);
    }

    @PutMapping("/{file-type}/{file-id}/file-info")
    public void updateFileInfo(
        @PathVariable("file-id") int fileID,
        @RequestBody SavedFileDTO savedFileDTO,
        @PathVariable("file-type") FileType fileType
    ) {
        fileStorageService.updateFileInfo(fileID, fileType, savedFileDTO);
    }
}
