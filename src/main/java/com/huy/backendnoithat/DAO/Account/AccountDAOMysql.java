package com.huy.backendnoithat.DAO.Account;

import com.huy.backendnoithat.Entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
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
        TypedQuery<Account> query = entityManager.createQuery("from Account", Account.class);
        return query.getResultList();
    }
    @Override
    public Account findById(int id) {
        return entityManager.find(Account.class, id);
    }
    @Override
    public Account findByUsername(String username) {
        TypedQuery<Account> query = entityManager.createQuery("from Account where username = :username", Account.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }
    @Override
    @Transactional
    public void save(Account account) {
        entityManager.persist(account);
    }
    @Override
    @Transactional
    public void deleteById(int id) {
        Account account = entityManager.find(Account.class, id);
        entityManager.remove(account);
    }
    @Override
    @Transactional
    public void activateAccount(int id) {
        Account account = entityManager.find(Account.class, id);
        account.setActive(true);
        updateAccount(account);
    }
    @Override
    @Transactional
    public void deactivateAccount(int id) {
        Account account = entityManager.find(Account.class, id);
        account.setActive(false);
        updateAccount(account);
    }
    @Override
    @Transactional
    public void updateAccount(Account account) {
        entityManager.merge(account);
    }

    @Override
    public List<Account> findAllNotEnabled() {
        TypedQuery<Account> query = entityManager.createQuery("from Account where enabled = false", Account.class);
        return query.getResultList();
    }
    @Override
    public List<Account> findAllEnabled() {
        TypedQuery<Account> query = entityManager.createQuery("from Account where enabled = true", Account.class);
        return query.getResultList();
    }
    @Transactional
    @Override
    public void enableAccount(int id) {
        Account account = entityManager.find(Account.class, id);
        account.setEnabled(true);
        updateAccount(account);
    }
    @Transactional
    @Override
    public void disableAccount(int id) {
        Account account = entityManager.find(Account.class, id);
        account.setEnabled(false);
        updateAccount(account);
    }


}
