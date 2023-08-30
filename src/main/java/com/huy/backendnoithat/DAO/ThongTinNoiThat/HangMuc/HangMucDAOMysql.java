package com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.Entity.HangMuc;
import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("hangMucDAOMysql")
public class HangMucDAOMysql implements HangMucDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<HangMuc> findAll() {
        TypedQuery<HangMuc> query = entityManager.createQuery("from HangMuc pc ORDER BY pc.id", HangMuc.class);
        return query.getResultList();
    }
    @Override
    public HangMuc findById(int id) {
        return entityManager.find(HangMuc.class, id);
    }
    @Override
    public HangMuc findUsingName(String name) {
        TypedQuery<HangMuc> query = entityManager.createQuery("from HangMuc where name = :name", HangMuc.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
    @Override
    public void save(HangMuc hangMuc) {
        entityManager.persist(hangMuc);
    }
    @Override
    public void deleteById(int id) {
        HangMuc hangMuc = entityManager.find(HangMuc.class, id);
        entityManager.remove(hangMuc);
    }
    @Override
    public void update(HangMuc hangMuc) {
        entityManager.merge(hangMuc);
    }
    @Override
    public List<HangMuc> findAllAndJoinFetch() {
        return null;
    }
    @Override
    public HangMuc findByIdAndJoinFetch(int id) {
        return null;
    }
}
