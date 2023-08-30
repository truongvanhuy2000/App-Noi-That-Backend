package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.Entity.AccountEntity;

import java.util.List;

public interface AccountService {
    List<AccountEntity> findAll();
    AccountEntity findById(int id);
    AccountEntity findByUsername(String username);
    void save(AccountEntity accountEntity);
    void deleteById(int id);
    void activateAccount(int id);
    void deactivateAccount(int id);
    List<AccountEntity> findAllNotEnabled();
    void enableAccount(int id);
    void disableAccount(int id);
    List<AccountEntity> findAllEnabled();
}
