package com.huy.backendnoithat.controller;

import com.huy.backendnoithat.service.general.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file-storage")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FileStorageController {
    private final FileStorageService fileStorageService;

    @PostMapping("/save-nt-file")
    public void saveNtFile(@RequestParam("file") MultipartFile multipartFile) {
        fileStorageService.saveNtFile(multipartFile);
    }
}
