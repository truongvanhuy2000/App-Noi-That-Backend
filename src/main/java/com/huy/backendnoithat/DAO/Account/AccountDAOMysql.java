package com.huy.backendnoithat.DAO.Account;

import com.huy.backendnoithat.Entity.Account.AccountEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "mysqlAccountDAO")
public class AccountDAOMysql implements AccountDAO{
    private final EntityManager entityManager;
    @Autowired
    public AccountDAOMysql(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<AccountEntity> findAll() {
        TypedQuery<AccountEntity> query = entityManager.createQuery("from AccountEntity acc join fetch acc.roleEntity", AccountEntity.class);
        return query.getResultList();
    }
    @Override
    public AccountEntity findById(int id) {
        return entityManager.find(AccountEntity.class, id);
    }
    @Override
    public AccountEntity findByUsername(String username) {
        TypedQuery<AccountEntity> query = entityManager.createQuery("from AccountEntity acc join fetch acc.roleEntity where acc.username = :username", AccountEntity.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }
    @Override
    @Transactional
    public void save(AccountEntity accountEntity) {
        entityManager.persist(accountEntity);
    }
    @Override
    public void update(AccountEntity accountEntity) {
        entityManager.merge(accountEntity);
    }
    @Override
    @Transactional
    public void deleteById(int id) {
        AccountEntity accountEntity = entityManager.find(AccountEntity.class, id);
        entityManager.remove(accountEntity);
    }
    @Override
    @Transactional
    public void activateAccount(int id) {
        AccountEntity accountEntity = entityManager.find(AccountEntity.class, id);
        accountEntity.setActive(true);
        updateAccount(accountEntity);
    }
    @Override
    @Transactional
    public void deactivateAccount(int id) {
        AccountEntity accountEntity = entityManager.find(AccountEntity.class, id);
        accountEntity.setActive(false);
        updateAccount(accountEntity);
    }
    @Override
    @Transactional
    public void updateAccount(AccountEntity accountEntity) {
        entityManager.merge(accountEntity);
    }

    @Override
    public List<AccountEntity> findAllNotEnabled() {
        TypedQuery<AccountEntity> query = entityManager.createQuery("from AccountEntity where enabled = false", AccountEntity.class);
        return query.getResultList();
    }
    @Override
    public List<AccountEntity> findAllEnabled() {
        TypedQuery<AccountEntity> query = entityManager.createQuery("from AccountEntity where enabled = true", AccountEntity.class);
        return query.getResultList();
    }
    @Transactional
    @Override
    public void enableAccount(int id) {
        AccountEntity accountEntity = entityManager.find(AccountEntity.class, id);
        accountEntity.setEnabled(true);
        updateAccount(accountEntity);
    }
    @Transactional
    @Override
    public void disableAccount(int id) {
        AccountEntity accountEntity = entityManager.find(AccountEntity.class, id);
        accountEntity.setEnabled(false);
        updateAccount(accountEntity);
    }


}
