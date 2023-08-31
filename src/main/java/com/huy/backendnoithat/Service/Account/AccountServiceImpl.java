package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DAO.Account.AccountDAO;
import com.huy.backendnoithat.Entity.Account.AccountEntity;
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
    public List<AccountEntity> findAll() {
        return accountDAO.findAll();
    }

    @Override
    public AccountEntity findById(int id) {
        return accountDAO.findById(id);
    }

    @Override
    public AccountEntity findByUsername(String username) {
        return accountDAO.findByUsername(username);
    }

    @Override
    public void save(AccountEntity accountEntity) {
        accountDAO.save(accountEntity);
    }

    @Override
    public void update(AccountEntity accountEntity) {
        accountDAO.update(accountEntity);
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
    public List<AccountEntity> findAllNotEnabled() {
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
    public List<AccountEntity> findAllEnabled() {
        return accountDAO.findAllEnabled();
    }

}
