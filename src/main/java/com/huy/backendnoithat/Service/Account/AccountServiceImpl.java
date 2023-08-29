package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DAO.Account.AccountDAO;
import com.huy.backendnoithat.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service(value = "defaultAccountService")
public class AccountServiceImpl implements AccountService {
    AccountDAO accountDAO;
    @Autowired
    public AccountServiceImpl(@Qualifier("mysqlAccountDAO") AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    @Override
    public List<Account> findAll() {
        return accountDAO.findAll();
    }

    @Override
    public Account findById(int id) {
        return accountDAO.findById(id);
    }

    @Override
    public Account findByUsername(String username) {
        return accountDAO.findByUsername(username);
    }

    @Override
    public void save(Account account) {
        accountDAO.save(account);
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
        return accountDAO.findAllNotEnabled();
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
        return accountDAO.findAllEnabled();
    }
}
