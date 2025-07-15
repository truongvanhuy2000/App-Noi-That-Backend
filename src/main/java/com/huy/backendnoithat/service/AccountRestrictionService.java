package com.huy.backendnoithat.service;

import com.huy.backendnoithat.dao.v1.AccountEntityDAO;
import com.huy.backendnoithat.entity.account.AccountEntity;
import com.huy.backendnoithat.model.FileSearchRequest;
import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.enums.FileType;
import com.huy.backendnoithat.service.file.FileStorageService;
import com.huy.backendnoithat.service.v1.AccountManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountRestrictionService {
    private final AccountEntityDAO accountEntityDAO;
    private final SheetService sheetService;
    private final FileStorageService fileStorageService;

    public boolean isAccountExpired(int id) {
        AccountEntity accountEntity = accountEntityDAO.findById(id).orElseThrow();
        if (accountEntity.getAccountRestrictionEntity() == null) {
            return false; // No restrictions, not expired
        }
        long currentTime = System.currentTimeMillis();
        return accountEntity.getAccountRestrictionEntity().getExpiredTimestamp() < currentTime;
    }

    public boolean isAccountReachFileUploadLimit(int id, FileType fileType) {
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
