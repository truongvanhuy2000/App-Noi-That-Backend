package com.huy.backendnoithat.service;

import com.huy.backendnoithat.dao.v1.AccountEntityDAO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.entity.Account.AccountRestrictionEntity;
import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.SheetSearchRequest;
import com.huy.backendnoithat.service.v1.AccountManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountRestrictionService {
    private final AccountEntityDAO accountEntityDAO;
    private final SheetService sheetService;

    public boolean isAccountExpired(int id) {
        AccountEntity accountEntity = accountEntityDAO.findById(id).orElseThrow();
        if (accountEntity.getAccountRestrictionEntity() == null) {
            return false; // No restrictions, not expired
        }
        long currentTime = System.currentTimeMillis();
        return accountEntity.getAccountRestrictionEntity().getExpiredTimestamp() < currentTime;
    }

    public boolean isAccountReachFileUploadLimit(int id) {
        AccountEntity accountEntity = accountEntityDAO.findById(id).orElseThrow();
        var result = sheetService.searchSheetFiles(id, PaginationRequest.builder().size(0).page(0).build(), new SheetSearchRequest());
        if (accountEntity.getAccountRestrictionEntity() == null) {
            return result.getTotal() >= AccountManagementService.DEFAULT_FILE_LIMIT;
        } else {
            return result.getTotal() >= accountEntity.getAccountRestrictionEntity().getFileLimit();
        }
    }
}
