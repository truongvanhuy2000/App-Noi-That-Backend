package com.huy.backendnoithat.controller.v1;

import com.huy.backendnoithat.exception.FileStorageException;
import com.huy.backendnoithat.exception.errorCode.FileStorageErrorCode;
import com.huy.backendnoithat.model.*;
import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.model.dto.SheetDataExportDTO;
import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.service.AccountRestrictionService;
import com.huy.backendnoithat.service.SheetService;
import com.huy.backendnoithat.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.huytv.exception.ExportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/sheet")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SheetController {
    private final SheetService sheetService;
    private final AccountRestrictionService accountRestrictionService;

    @GetMapping(value = "/share/{fileId}")
    public PreSignedToken shareSheetFile(@PathVariable(value = "fileId", required = false) Integer fileId) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return sheetService.shareSheetFile(fileId, userID);
    }

    @GetMapping(value = "/{file-id}")
    public ResponseEntity<Resource> getSheetFile(@PathVariable("file-id") int fileID) throws IOException {
        SheetFileDTO sheetFile = sheetService.getSheetFile(fileID);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(sheetFile.getSize());
        Resource resource = new ByteArrayResource(sheetFile.getContent());
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public PaginationResponse<List<SavedFileDTO>> searchSheetFiles(
        @ModelAttribute PaginationRequest paginationRequest,
        @ModelAttribute SheetSearchRequest sheetSearchRequest
    ) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return sheetService.searchSheetFiles(userID, paginationRequest, sheetSearchRequest);
    }

    @PostMapping("")
    public SheetFileDTO saveNoiThatFile(
        @RequestParam(value = "fileId", required = false) Integer fileId,
        @RequestBody SheetDataExportDTO sheetDataExportDTO
    ) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        if (accountRestrictionService.isAccountReachFileUploadLimit(userID, FileType.NT_FILE)) {
            throw new FileStorageException(FileStorageErrorCode.FILE_LIMIT_REACHED);
        }
        try {
            return sheetService.saveSheetFile(fileId, sheetDataExportDTO);
        } catch (IOException | ExportException e) {
            log.error("Error saving Noi That file: {}", e.getMessage());
            throw new RuntimeException("Failed to save Noi That file", e);
        }
    }

    @GetMapping(value = "/company-logo")
    public ResponseEntity<Resource> getCompanyLogo() {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        byte[] logo = sheetService.getCompanyLogo(userID);
        if (logo == null || logo.length == 0) {
            return null;
        }
        Resource resource = new ByteArrayResource(logo);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping(value = "/company-logo", consumes = "multipart/form-data")
    public ResponseEntity<SavedFileDTO> uploadCompanyLogo(@RequestParam("file") MultipartFile file) throws IOException {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        var uploadFile = UploadFile.builder()
            .contentType(file.getContentType())
            .fileName(file.getOriginalFilename())
            .size(file.getSize())
            .inputStream(file.getInputStream())
            .build();
        SavedFileDTO savedFileDTO = sheetService.uploadCompanyLogo(userID, uploadFile);
        return ResponseEntity.ok(savedFileDTO);
    }

    @GetMapping(value = "/stats")
    public SheetStats getSheetStats() {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return sheetService.getSheetStats(userID);
    }
}
