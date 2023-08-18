package com.huy.backendnoithat.DAO.Account;

import com.huy.backendnoithat.Entity.Account;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "mysqlAccountDAO")
public class AccountDAOMysql implements AccountDAO{
    private EntityManager entityManager;
    @Autowired
    public AccountDAOMysql(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Account> findAll() {

        return null;
    }

    @Override
    public Account findById(int id) {
        return null;
    }

    @Override
    public Account findByUsername(String username) {
        return null;
    }

    @Override
    public void save(Account account) {
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void activateAccount(int id) {

    }
}
