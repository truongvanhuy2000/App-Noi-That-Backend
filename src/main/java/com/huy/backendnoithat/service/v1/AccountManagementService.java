package com.huy.backendnoithat.service.v1;

import com.blazebit.persistence.PagedList;
import com.huy.backendnoithat.dao.v1.AccountEntityDAO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.entity.Account.RoleEntity;
import com.huy.backendnoithat.manager.AccountEntityManager;
import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.PaginationResponse;
import com.huy.backendnoithat.model.UserSearchRequest;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.AccountManagement.AccountInformation;
import com.huy.backendnoithat.model.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        if (account.getExpiredDate() != null) {
            accountEntity.setExpiredDate(new Date(Date.valueOf(account.getExpiredDate()).getTime()));
        }
        if (account.getActive() != null) {
            accountEntity.setActive(account.getActive());
        }
        if (account.getEnabled() != null) {
            accountEntity.setEnabled(account.getEnabled());
        }
        if (StringUtils.isNotEmpty(account.getPassword())) {
            accountEntity.setPassword(account.getPassword());
        }
        if (account.getRoles() != null && !account.getRoles().isEmpty()) {
            // Each account has one role, so we assume the first role is the user role
            String roleValue = account.getRoles().get(0);
            if (roleValue == null || roleValue.isEmpty()) {
                throw new RuntimeException("Role cannot be null or empty");
            }
            if (roleValue.equals(UserRole.USER.value) || roleValue.equals(UserRole.ADMIN.value)) {
                RoleEntity userRole = accountEntity.getRoleEntity().get(0);
                userRole.setRole(roleValue);
            } else {
                throw new RuntimeException("Invalid role: " + roleValue);
            }

        }
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
        accountEntity.setEnabled(account.getActive());
        AccountEntity ret = accountEntityDAO.save(accountEntity);
        return new Account(ret);
    }
}
