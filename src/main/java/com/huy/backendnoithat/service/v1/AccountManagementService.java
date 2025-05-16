package com.huy.backendnoithat.service.v1;

import com.blazebit.persistence.PagedList;
import com.huy.backendnoithat.dao.v1.AccountEntityDAO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.manager.AccountEntityManager;
import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.PaginationResponse;
import com.huy.backendnoithat.model.UserSearchRequest;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.AccountManagement.AccountInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AccountManagementService {
    private final AccountEntityManager accountEntityManager;
    private final AccountEntityDAO accountEntityDAO;

    public PaginationResponse<List<Account>> search(
        PaginationRequest paginationRequest,
        UserSearchRequest userSearchRequest
    ) {
        int page = paginationRequest.getPage() == null ? 0 : paginationRequest.getPage();
        int size = paginationRequest.getSize() == null ? 10 : paginationRequest.getSize();

        PagedList<AccountEntity> pagedList = accountEntityManager.search(page, size, userSearchRequest);
        List<Account> accountList = pagedList.stream()
            .map(Account::new)
            .toList();
        return PaginationResponse.<List<Account>>builder()
            .total(pagedList.getTotalSize())
            .data(accountList)
            .build();
    }

    @Transactional
    public void update(int id, Account account) {
        AccountEntity accountEntity = accountEntityDAO.findById(id).orElseThrow();
        accountEntity.setExpiredDate(new Date(Date.valueOf(account.getExpiredDate()).getTime()));
        accountEntity.setActive(account.isActive());
        accountEntity.setEnabled(account.isEnabled());
        accountEntity.setPassword(account.getPassword());
    }

    @Transactional
    public Account save(Account account) {
        AccountEntity accountEntity = accountEntityDAO.findByUsername(account.getUsername()).orElse(null);
        if (accountEntity != null) {
            throw new RuntimeException("Username already exists");
        }
        if (account.getAccountInformation() == null) {
            account.setAccountInformation(AccountInformation.builder().name("anon").build());
        }
        accountEntity = new AccountEntity(account);
        accountEntity.setEnabled(account.isActive());
        AccountEntity ret = accountEntityDAO.save(accountEntity);
        return new Account(ret);
    }
}
