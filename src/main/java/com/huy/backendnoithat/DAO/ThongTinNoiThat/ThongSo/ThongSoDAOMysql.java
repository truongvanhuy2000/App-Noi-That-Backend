package com.huy.backendnoithat.DAO.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.Entity.ThongSo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("thongSoDAOMysql")
public class ThongSoDAOMysql implements ThongSoDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<ThongSo> findAll() {
        TypedQuery<ThongSo> query = entityManager.createQuery("from ThongSo", ThongSo.class);
        return query.getResultList();
    }

    @Override
    public ThongSo findById(int id) {
        return entityManager.find(ThongSo.class, id);
    }

    @Override
    public ThongSo findUsingName(String name) {
        return null;
    }

    @Override
    public void save(ThongSo thongSo) {
        entityManager.persist(thongSo);
    }

    @Override
    public void deleteById(int id) {
        ThongSo thongSo = entityManager.find(ThongSo.class, id);
        entityManager.remove(thongSo);
    }

    @Override
    public void update(ThongSo thongSo) {
        entityManager.merge(thongSo);
    }

    @Override
    public List<ThongSo> findAllAndJoinFetch() {
        return findAll();
    }

    @Override
    public ThongSo findByIdAndJoinFetch(int id) {
        return findById(id);
    }
}

