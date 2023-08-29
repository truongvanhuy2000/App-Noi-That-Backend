package com.huy.backendnoithat.DAO.Account;

import com.huy.backendnoithat.Entity.Account;

import java.util.List;

public interface AccountDAO {
    List<Account> findAll();
    Account findById(int id);
    Account findByUsername(String username);
    void save(Account account);
    void deleteById(int id);
    void activateAccount(int id);
    void deactivateAccount(int id);
    void updateAccount(Account account);
    List<Account> findAllNotEnabled();
    void enableAccount(int id);
    void disableAccount(int id);
    List<Account> findAllEnabled();
}
