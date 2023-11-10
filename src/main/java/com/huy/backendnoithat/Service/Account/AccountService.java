package com.huy.backendnoithat.Service.Account;


import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.AccountManagement.AccountInformation;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account findById(int id);
    Account findByUsername(String username);
    void save(Account Account);
    void update(Account Account);
    void deleteById(int id);
    void activateAccount(int id);
    void deactivateAccount(int id);
    List<Account> findAllNotEnabled();
    void enableAccount(int id);
    void disableAccount(int id);
    List<Account> findAllEnabled();

    void changePassword(String username, String oldPassword, String newPassword);

    void updateInfo(String username, AccountInformation accountInformation);
}
