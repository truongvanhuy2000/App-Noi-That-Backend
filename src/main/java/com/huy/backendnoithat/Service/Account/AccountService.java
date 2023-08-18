package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.Entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account findById(int id);
    Account findByUsername(String username);
    void save(Account account);
    void deleteById(int id);
    void activateAccount(int id);
}
