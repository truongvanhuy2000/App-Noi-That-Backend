package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DAO.Account.AccountDAO;
import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.AccountManagement.AccountInformation;
import com.huy.backendnoithat.Entity.Account.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "defaultAccountService")
public class AccountServiceImpl implements AccountService {
    AccountDAO accountDAO;
    final PasswordEncoder passwordEncoder;
    @Autowired
    public AccountServiceImpl(@Qualifier("mysqlAccountDAO") AccountDAO accountDAO,
                              PasswordEncoder passwordEncoder) {
        this.accountDAO = accountDAO;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<Account> findAll() {
        List<AccountEntity> accountEntities = accountDAO.findAll();
        return accountEntities.stream().map(Account::new).toList();
    }
    @Override
    public Account findById(int id) {
        return new Account(accountDAO.findById(id));
    }
    @Override
    public Account findByUsername(String username) {
        return new Account(accountDAO.findByUsername(username));
    }
    @Override
    public void save(Account Account) {
        Account.setPassword(passwordEncoder.encode(Account.getPassword()));
        accountDAO.save(new AccountEntity(Account));
    }
    @Override
    public void update(Account Account) {
        Account.setPassword(passwordEncoder.encode(Account.getPassword()));
        accountDAO.update(new AccountEntity(Account));
    }
    @Override
    public void deleteById(int id) {
        accountDAO.deleteById(id);
    }
    @Override
    public void activateAccount(int id) {
        accountDAO.activateAccount(id);
    }
    @Override
    public void deactivateAccount(int id) {
        accountDAO.deactivateAccount(id);
    }

    @Override
    public List<Account> findAllNotEnabled() {
        return accountDAO.findAllNotEnabled().stream().map(Account::new).toList();
    }

    @Override
    public void enableAccount(int id) {
        accountDAO.enableAccount(id);
    }

    @Override
    public void disableAccount(int id) {
        accountDAO.disableAccount(id);
    }

    @Override
    public List<Account> findAllEnabled() {
        return accountDAO.findAllEnabled().stream().map(Account::new).toList();
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        String currentPassword = accountDAO.findByUsername(username).getPassword();
        if (!passwordEncoder.matches(oldPassword, currentPassword)) {
            throw new RuntimeException("Old password is not match");
        }
        accountDAO.changePassword(username, passwordEncoder.encode(newPassword));
    }

    @Override
    public void updateInfo(String username, AccountInformation accountInformation) {
        accountDAO.updateInfo(username, accountInformation);
    }
}
