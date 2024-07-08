package com.huy.backendnoithat.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-storage")
public class FileStorageController {
    @PostMapping("/save-nt-file")
    public void saveNtFile() {

    }
}
