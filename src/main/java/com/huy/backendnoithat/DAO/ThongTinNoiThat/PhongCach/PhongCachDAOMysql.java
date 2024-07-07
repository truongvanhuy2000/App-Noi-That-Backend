package com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.Constant.AccountConstant;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("phongCachDAOMysql")
public class PhongCachDAOMysql implements PhongCachDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<PhongCachNoiThatEntity> findAll(String owner) {
        TypedQuery<PhongCachNoiThatEntity> query = entityManager.createQuery(
                "from PhongCachNoiThatEntity pc " +
                        "where pc.account.username = :owner " +
                        "ORDER BY pc.id", PhongCachNoiThatEntity.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }
    @Override
    public PhongCachNoiThatEntity findById(String owner, int id) {
        TypedQuery<PhongCachNoiThatEntity> query = entityManager.createQuery(
                "from PhongCachNoiThatEntity pc " +
                        "where pc.account.username = :owner " +
                        "and pc.id = :id", PhongCachNoiThatEntity.class);
        query.setParameter("owner", owner);
        query.setParameter("id", id);
        return query.getSingleResult();
    }
    @Override
    public PhongCachNoiThatEntity findUsingName(String owner, String name) {
        TypedQuery<PhongCachNoiThatEntity> query = entityManager.createQuery(
                "from PhongCachNoiThatEntity pc " +
                        "where pc.name = :name " +
                        "and pc.account.username = :owner", PhongCachNoiThatEntity.class);
        query.setParameter("name", name);
        query.setParameter("owner", owner);
        return query.getSingleResult();
    }
    @Override
    @Transactional
    public void save(String owner, PhongCachNoiThatEntity phongCachNoiThatEntity) {
        Query query = entityManager.createQuery(
                "insert into PhongCachNoiThatEntity (name, account) " +
                        "select :name, a " +
                        "from AccountEntity a " +
                        "where a.username = :owner");
        query.setParameter("name", phongCachNoiThatEntity.getName());
        query.setParameter("owner", owner);
        query.executeUpdate();
    }
    @Override
    @Transactional
    public void deleteById(String owner, int id) {
        Query query = entityManager.createQuery(
                "delete from PhongCachNoiThatEntity pc " +
                        "where pc.id = :id " +
                        "and pc.account.id = (select a.id from AccountEntity a where a.username = :owner)");
        query.setParameter("id", id);
        query.setParameter("owner", owner);
        query.executeUpdate();
    }
    @Override
    @Transactional
    public void update(String owner, PhongCachNoiThatEntity phongCachNoiThatEntity) {
        Query query = entityManager.createQuery(
                "update PhongCachNoiThatEntity pc" +
                        " set pc.name = :name" +
                        " where pc.id = :id " +
                        "and pc.account.id = (select a.id from AccountEntity a where a.username = :owner)");
        query.setParameter("name", phongCachNoiThatEntity.getName());
        query.setParameter("id", phongCachNoiThatEntity.getId());
        query.setParameter("owner", owner);
        query.executeUpdate();
    }
    @Override
    public List<PhongCachNoiThatEntity> findAllAndJoinFetch(String owner) {
        TypedQuery<PhongCachNoiThatEntity> query = entityManager.createQuery(
                "FROM PhongCachNoiThatEntity p " +
                        "JOIN FETCH p.noiThatEntity nt "
                , PhongCachNoiThatEntity.class);
        return query.getResultList();
    }
    // Not used
    @Override
    public PhongCachNoiThatEntity findByIdAndJoinFetch(String owner, int id) {
        String jpql = "SELECT DISTINCT a FROM PhongCachNoiThatEntity a "
                + "JOIN FETCH a.noiThatEntity b "
                + "WHERE a.id = :id";
        TypedQuery<PhongCachNoiThatEntity> query = entityManager.createQuery(jpql, PhongCachNoiThatEntity.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void copySampleDataFromAdmin(int id) {
        String jpql = "INSERT INTO phongcachnoithat (name, account_id) " +
                "SELECT pc.name, :id FROM phongcachnoithat pc " +
                "WHERE account_id = :adminId";
        Query query = entityManager.createNativeQuery(jpql);
        query.setParameter("id", id);
        query.setParameter("adminId", AccountConstant.ADMIN_ID);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void swap(int accountId, int id1, int id2) {
        String jpql = "update phongcachnoithat pc1, phongcachnoithat pc2 " +
                "LEFT JOIN noithat n1 ON n1.phong_cach_id = :id1 " +
                "LEFT JOIN noithat n2 ON n2.phong_cach_id = :id2 " +
                "set pc1.name = pc2.name, " +
                "pc2.name = pc1.name, " +
                "n1.phong_cach_id = :id2, " +
                "n2.phong_cach_id = :id1 " +
                "where pc1.id = :id1 and pc2.id = :id2";
        Query query = entityManager.createNativeQuery(jpql);
        query.setParameter("id1", id1);
        query.setParameter("id2", id2);
        query.executeUpdate();
    }
}
