package com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach;

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
}
