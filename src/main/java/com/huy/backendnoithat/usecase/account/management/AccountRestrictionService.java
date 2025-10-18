package com.huy.backendnoithat.usecase.account.management;

import com.huy.backendnoithat.dao.v1.AccountEntityDAO;
import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.controller.file.dto.FileSearchRequest;
import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.usecase.sheet.SheetService;
import com.huy.backendnoithat.usecase.file.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountRestrictionService {
    private final AccountEntityDAO accountEntityDAO;
    private final SheetService sheetService;
    private final FileStorageService fileStorageService;

    public boolean isAccountExpired(int id) {
        log.info("START -- isAccountExpired {}", id);

        AccountEntity accountEntity = accountEntityDAO.findById(id).orElseThrow();
        if (accountEntity.getAccountRestrictionEntity() == null) {
            return false; // No restrictions, not expired
        }
        long currentTime = System.currentTimeMillis();
        return accountEntity.getAccountRestrictionEntity().getExpiredTimestamp() < currentTime;
    }

    public boolean isAccountReachFileUploadLimit(int id, FileType fileType) {
        log.info("START -- isAccountReachFileUploadLimit id {} fileType {}", id, fileType);

        AccountEntity accountEntity = accountEntityDAO.findById(id).orElseThrow();
        var result = fileStorageService.find(
            PaginationRequest.builder().size(1).page(0).build(),
            FileSearchRequest.builder().userId(id).fileType(fileType).build()
        );
        if (accountEntity.getAccountRestrictionEntity() == null) {
            return result.getTotal() >= AccountManagementService.DEFAULT_FILE_LIMIT;
        } else {
            return result.getTotal() >= accountEntity.getAccountRestrictionEntity().getFileLimit();
        }
    }
}
