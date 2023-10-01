package com.huy.backendnoithat.DAO.Account;

import com.huy.backendnoithat.Entity.Account.AccountEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
        TypedQuery<AccountEntity> query = entityManager.createQuery(
                "from AccountEntity acc " +
                        "join fetch acc.roleEntity " +
                        "join fetch acc.accountInformationEntity", AccountEntity.class);
        return query.getResultList();
    }
    @Override
    public AccountEntity findById(int id) {
        TypedQuery<AccountEntity> query = entityManager.createQuery(
                "from AccountEntity acc " +
                        "join fetch acc.roleEntity " +
                        "join fetch acc.accountInformationEntity " +
                        "where acc.id = :id", AccountEntity.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
    @Override
    public AccountEntity findByUsername(String username) {
        TypedQuery<AccountEntity> query = entityManager.createQuery(
                "from AccountEntity acc " +
                        "join fetch acc.roleEntity " +
                        "join fetch acc.accountInformationEntity " +
                        "where acc.username = :username", AccountEntity.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }
    @Override
    @Transactional
    public void save(AccountEntity accountEntity) {
        entityManager.persist(accountEntity);
    }
    @Override
    @Transactional
    public void update(AccountEntity accountEntity) {
        Query query = entityManager.createQuery(
                "UPDATE AccountEntity acc SET acc.username = :username, acc.password = :password WHERE acc.id = :id");
        query.setParameter("username", accountEntity.getUsername());
        query.setParameter("password", accountEntity.getPassword());
        query.setParameter("id", accountEntity.getId());
        query.executeUpdate();
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
        Query query = entityManager.createQuery(
                "UPDATE AccountEntity acc SET acc.active = true WHERE acc.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
    @Override
    @Transactional
    public void deactivateAccount(int id) {
        Query query = entityManager.createQuery(
                "UPDATE AccountEntity acc SET acc.active = false WHERE acc.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
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
        Query query = entityManager.createQuery(
                "UPDATE AccountEntity acc SET acc.enabled = true WHERE acc.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
    @Transactional
    @Override
    public void disableAccount(int id) {
        Query query = entityManager.createQuery(
                "UPDATE AccountEntity acc SET acc.enabled = false WHERE acc.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }


}
