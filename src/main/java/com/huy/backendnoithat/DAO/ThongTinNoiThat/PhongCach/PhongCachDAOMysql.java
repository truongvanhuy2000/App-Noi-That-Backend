package com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("phongCachDAOMysql")
@Transactional(readOnly = true)
public class PhongCachDAOMysql implements PhongCachDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<PhongCachNoiThat> findAll() {
        TypedQuery<PhongCachNoiThat> query = entityManager.createQuery("from PhongCachNoiThat pc ORDER BY pc.id", PhongCachNoiThat.class);
        return query.getResultList();
    }
    @Override
    public PhongCachNoiThat findById(int id) {
        return entityManager.find(PhongCachNoiThat.class, id);
    }
    @Override
    public PhongCachNoiThat findUsingName(String name) {
        TypedQuery<PhongCachNoiThat> query = entityManager.createQuery("from PhongCachNoiThat where name = :name", PhongCachNoiThat.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
    @Override
    @Transactional
    public void save(PhongCachNoiThat phongCachNoiThat) {
        entityManager.persist(phongCachNoiThat);
    }
    @Override
    public void deleteById(int id) {
        PhongCachNoiThat phongCachNoiThat = entityManager.find(PhongCachNoiThat.class, id);
        entityManager.remove(phongCachNoiThat);
    }
    @Override
    @Transactional
    public void update(PhongCachNoiThat phongCachNoiThat) {
        entityManager.merge(phongCachNoiThat);
    }
    @Override
    public List<PhongCachNoiThat> findAllAndJoinFetch() {
        TypedQuery<PhongCachNoiThat> query = entityManager.createQuery(
                "FROM PhongCachNoiThat pc "
                        + "JOIN FETCH pc.noiThat "
                        + "ORDER BY pc.id "
                , PhongCachNoiThat.class);
        return query.getResultList();
    }
    @Override
    public PhongCachNoiThat findByIdAndJoinFetch(int id) {
        TypedQuery<PhongCachNoiThat> query = entityManager.createQuery(
                "SELECT pc FROM PhongCachNoiThat pc "
                        + "JOIN FETCH pc.noiThat "
                        + "WHERE pc.id = :id "
                        + "ORDER BY pc.id "
                , PhongCachNoiThat.class);
        entityManager.setProperty("id", id);
        return query.getSingleResult();
    }
}
