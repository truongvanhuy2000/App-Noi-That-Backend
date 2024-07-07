package com.huy.backendnoithat.DAO.Account;

import com.huy.backendnoithat.DTO.AccountManagement.AccountInformation;
import com.huy.backendnoithat.Entity.Account.AccountEntity;

import java.util.List;

public interface AccountDAO {
    List<AccountEntity> findAll();
    AccountEntity findById(int id);
    AccountEntity findByUsername(String username);
    void save(AccountEntity accountEntity);
    int update(AccountEntity accountEntity);
    void deleteById(int id);
    int activateAccount(int id);
    int deactivateAccount(int id);
    List<AccountEntity> findAllNotEnabled();
    int enableAccount(int id);
    int disableAccount(int id);
    List<AccountEntity> findAllEnabled();
    int changePassword(String username, String newPassword);

    int updateInfo(String username, AccountInformation accountInformation);
}
