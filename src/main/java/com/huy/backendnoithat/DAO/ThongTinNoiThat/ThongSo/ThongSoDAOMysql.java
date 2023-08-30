package com.huy.backendnoithat.DAO.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.Entity.ThongSoEntity;
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
    public List<ThongSoEntity> findAll() {
        TypedQuery<ThongSoEntity> query = entityManager.createQuery("from ThongSoEntity", ThongSoEntity.class);
        return query.getResultList();
    }
    @Override
    public ThongSoEntity findById(int id) {
        return entityManager.find(ThongSoEntity.class, id);
    }
    @Override
    public ThongSoEntity findUsingName(String name) {
        return null;
    }
    @Override
    public void save(ThongSoEntity thongSoEntity) {
        entityManager.persist(thongSoEntity);
    }
    @Override
    public void deleteById(int id) {
        ThongSoEntity thongSoEntity = entityManager.find(ThongSoEntity.class, id);
        entityManager.remove(thongSoEntity);
    }
    @Override
    public void update(ThongSoEntity thongSoEntity) {
        entityManager.merge(thongSoEntity);
    }
    @Override
    public List<ThongSoEntity> findAllAndJoinFetch() {
        return findAll();
    }
    @Override
    public ThongSoEntity findByIdAndJoinFetch(int id) {
        return findById(id);
    }

    @Override
    public List<ThongSoEntity> searchByVatLieu(int id) {
        TypedQuery<ThongSoEntity> query = entityManager.createQuery("from ThongSoEntity pc where pc.vatLieu.id = :id", ThongSoEntity.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}

