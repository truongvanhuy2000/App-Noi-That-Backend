package com.huy.backendnoithat.service.general;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void saveNtFile(MultipartFile multipartFile);
}
