package com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.Entity.PhongCachNoiThatEntity;
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
    public List<PhongCachNoiThatEntity> findAll() {
        TypedQuery<PhongCachNoiThatEntity> query = entityManager.createQuery("from PhongCachNoiThatEntity pc ORDER BY pc.id", PhongCachNoiThatEntity.class);
        return query.getResultList();
    }
    @Override
    public PhongCachNoiThatEntity findById(int id) {
        return entityManager.find(PhongCachNoiThatEntity.class, id);
    }
    @Override
    public PhongCachNoiThatEntity findUsingName(String name) {
        TypedQuery<PhongCachNoiThatEntity> query = entityManager.createQuery("from PhongCachNoiThatEntity where name = :name", PhongCachNoiThatEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
    @Override
    @Transactional
    public void save(PhongCachNoiThatEntity phongCachNoiThatEntity) {
        entityManager.persist(phongCachNoiThatEntity);
    }
    @Override
    public void deleteById(int id) {
        PhongCachNoiThatEntity phongCachNoiThatEntity = entityManager.find(PhongCachNoiThatEntity.class, id);
        entityManager.remove(phongCachNoiThatEntity);
    }
    @Override
    @Transactional
    public void update(PhongCachNoiThatEntity phongCachNoiThatEntity) {
        entityManager.merge(phongCachNoiThatEntity);
    }
    @Override
    public List<PhongCachNoiThatEntity> findAllAndJoinFetch() {
        TypedQuery<PhongCachNoiThatEntity> query = entityManager.createQuery(
                "FROM PhongCachNoiThatEntity p " +
                        "LEFT JOIN FETCH p.noiThat nt " +
                        "LEFT JOIN FETCH nt.hangMuc hm " +
                        "LEFT JOIN FETCH hm.vatLieu vl " +
                        "LEFT JOIN FETCH vl.thongSo"
                , PhongCachNoiThatEntity.class);
        return query.getResultList();
    }
    @Override
    public PhongCachNoiThatEntity findByIdAndJoinFetch(int id) {
        TypedQuery<PhongCachNoiThatEntity> query = entityManager.createQuery(
                "SELECT pc FROM PhongCachNoiThatEntity pc "
                        + "JOIN FETCH pc.noiThat "
                        + "WHERE pc.id = :id "
                        + "ORDER BY pc.id "
                , PhongCachNoiThatEntity.class);
        entityManager.setProperty("id", id);
        return query.getSingleResult();
    }
}
