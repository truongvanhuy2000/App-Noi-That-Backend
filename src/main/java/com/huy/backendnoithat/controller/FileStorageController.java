package com.huy.backendnoithat.controller;

import com.huy.backendnoithat.model.*;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.service.AccountRestrictionService;
import com.huy.backendnoithat.service.file.FileStorageService;
import com.huy.backendnoithat.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/file-storage")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStorageController {
    private final FileStorageService fileStorageService;
    private final AccountRestrictionService accountRestrictionService;

    @PostMapping(value = "/{file-type}/upload", consumes = "multipart/form-data")
    public SavedFileDTO saveFile(
        @RequestParam("file") MultipartFile multipartFile,
        @PathVariable("file-type") FileType fileType
    ) throws IOException {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        if (accountRestrictionService.isAccountReachFileUploadLimit(userID, fileType)) {
            throw new RuntimeException("Account has reached the file upload limit");
        }
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

    @GetMapping(value = "/{file-type}/{file-id}/download")
    public ResponseEntity<Resource> downloadFile(
        @PathVariable("file-id") int fileID,
        @PathVariable("file-type") FileType fileType
    ) {
        SavedFileDTO savedFileDTO = fileStorageService.getFile(fileID, fileType);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(savedFileDTO.getSize());
        Resource inputStreamResource = new InputStreamResource(savedFileDTO.getInputStream());
        return new ResponseEntity<>(inputStreamResource, headers, HttpStatus.OK);
    }

    @GetMapping("/{file-type}/{file-id}/file-info")
    public SavedFileDTO getFileInfo(
        @PathVariable("file-id") int fileID,
        @PathVariable("file-type") FileType fileType
    ) {
        return fileStorageService.getFileInfo(fileID, fileType);
    }

    @GetMapping("/find")
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
