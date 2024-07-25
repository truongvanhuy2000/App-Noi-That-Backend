package com.huy.backendnoithat.controller;

import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.service.general.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/file-storage")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStorageController {
    private final FileStorageService fileStorageService;

    @PostMapping("/upload/nt-file")
    public SavedFileDTO saveNtFile(@RequestParam("file") MultipartFile multipartFile) {
        return fileStorageService.saveNtFile(multipartFile);
    }

    @PutMapping("/upload/nt-file/{file-id}")
    public void updateNtFile(@RequestParam("file") MultipartFile multipartFile, @PathVariable("file-id") int fileID) {
        fileStorageService.updateNtFile(multipartFile, fileID);
    }

    @GetMapping("/download/nt-file/{file-id}")
    public void getNtFile(HttpServletResponse httpResponse, @PathVariable("file-id") int fileID) throws IOException {
        SavedFileDTO savedFileDTO = fileStorageService.getNtFile(fileID, httpResponse.getOutputStream());
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename(savedFileDTO.getFileName())
                .build();
        httpResponse.setHeader(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
    }

    @GetMapping("/get-file/{file-id}")
    public SavedFileDTO getFileInfo(HttpServletResponse httpResponse, @PathVariable("file-id") int fileID) throws IOException {
        return fileStorageService.getFileInfo(fileID);
    }

    @GetMapping("/get-all")
    public List<SavedFileDTO> getAllFileOfUser() {
        return fileStorageService.getAllFileOfUser();
    }

    @DeleteMapping("/delete-nt-file/{file-id}")
    public void deleteNtFile(@PathVariable("file-id") int fileID) {
        fileStorageService.deleteNtFile(fileID);
    }

    @PutMapping("/update-nt-file/{file-id}")
    public void updateFileInfo(@PathVariable("file-id") int fileID, @RequestBody SavedFileDTO savedFileDTO) {
        fileStorageService.updateFileInfo(fileID, savedFileDTO);
    }
}
