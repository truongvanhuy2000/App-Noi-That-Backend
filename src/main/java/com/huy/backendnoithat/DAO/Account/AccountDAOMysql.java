package com.huy.backendnoithat.DAO.Account;

import com.huy.backendnoithat.DTO.AccountManagement.AccountInformation;
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
    public int update(AccountEntity accountEntity) {
        Query query = entityManager.createQuery(
                "UPDATE AccountEntity acc " +
                        "SET acc.username = " +
                        "case when :username is not null and :username != '' then :username else acc.username end, " +
                        "acc.password = " +
                        "case when :password is not null and :password != '' then :password else acc.password end, " +
                        "acc.expiredDate = " +
                        "case when :date is not null then :date else acc.expiredDate end " +
                        "WHERE acc.id = :id");
        query.setParameter("username", accountEntity.getUsername());
        query.setParameter("password", accountEntity.getPassword());
        query.setParameter("id", accountEntity.getId());
        query.setParameter("date", accountEntity.getExpiredDate());
        return query.executeUpdate();
    }
    @Override
    @Transactional
    public void deleteById(int id) {
        AccountEntity accountEntity = entityManager.find(AccountEntity.class, id);
        entityManager.remove(accountEntity);
    }
    @Override
    @Transactional
    public int activateAccount(int id) {
        Query query = entityManager.createQuery(
                "UPDATE AccountEntity acc SET acc.active = true WHERE acc.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    @Override
    @Transactional
    public int deactivateAccount(int id) {
        Query query = entityManager.createQuery(
                "UPDATE AccountEntity acc SET acc.active = false WHERE acc.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    @Override
    public List<AccountEntity> findAllNotEnabled() {
        TypedQuery<AccountEntity> query = entityManager.createQuery(
                "from AccountEntity " +
                        "where enabled = false", AccountEntity.class);
        return query.getResultList();
    }
    @Override
    public List<AccountEntity> findAllEnabled() {
        TypedQuery<AccountEntity> query = entityManager.createQuery(
                "from AccountEntity where enabled = true", AccountEntity.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public int changePassword(String username, String newPassword) {
        Query query = entityManager.createNativeQuery("update appnoithat.account " +
                "set password = :newPassword " +
                "where username = :username");
        query.setParameter("username", username);
        query.setParameter("newPassword", newPassword);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int updateInfo(String username, AccountInformation accountInformation) {
        Query query = entityManager.createNativeQuery("update appnoithat.account " +
                "JOIN appnoithat.accountinformation a ON a.id = account.info_id " +
                "SET a.name = :fullName, " +
                "a.email = :email, " +
                "a.phone = :phone, " +
                "a.gender = :gender, " +
                "a.address = :address " +
                "WHERE account.username = :username");

        query.setParameter("username", username);
        query.setParameter("fullName", accountInformation.getName());
        query.setParameter("email", accountInformation.getEmail());
        query.setParameter("phone", accountInformation.getPhone());
        query.setParameter("address", accountInformation.getAddress());
        query.setParameter("gender", accountInformation.getGender());

        return query.executeUpdate();
    }

    @Transactional
    @Override
    public int enableAccount(int id) {
        Query query = entityManager.createQuery(
                "UPDATE AccountEntity acc SET acc.enabled = true WHERE acc.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    @Transactional
    @Override
    public int disableAccount(int id) {
        Query query = entityManager.createQuery(
                "UPDATE AccountEntity acc SET acc.enabled = false WHERE acc.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }


}
