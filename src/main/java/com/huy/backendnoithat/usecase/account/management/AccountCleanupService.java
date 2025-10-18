package com.huy.backendnoithat.usecase.account.management;

import com.huy.backendnoithat.model.dto.SavedFileDTO;
import com.huy.backendnoithat.usecase.file.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountCleanupService {
    private final FileStorageService fileStorageService;

    @Async
    public void afterDeleteAccount(int accountId) {
        // Implement any additional cleanup logic here if needed
        log.info("Post-delete cleanup for account ID: {}", accountId);
        // For example, delete files associated with the account
        List<SavedFileDTO> savedFileDTOList = fileStorageService.getAllFileOfUserById(accountId);
        for (SavedFileDTO fileDTO : savedFileDTOList) {
            try {
                fileStorageService.deleteFile(fileDTO.getId(), fileDTO.getFileType());
                log.info("Deleted file ID: {} for account ID: {}", fileDTO.getId(), accountId);
            } catch (Exception e) {
                log.error("Failed to delete file ID: {} for account ID: {}", fileDTO.getId(), accountId, e);
            }
        }
    }
}
