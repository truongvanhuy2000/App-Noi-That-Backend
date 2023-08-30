package com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Entity.VatLieuEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("vatLieuDAOMysql")
public class VatLieuDAOMysql implements VatLieuDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<VatLieuEntity> findAll() {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery("from VatLieuEntity vl order by vl.id", VatLieuEntity.class);
        return query.getResultList();

    }
    @Override
    public VatLieuEntity findById(int id) {
        return entityManager.find(VatLieuEntity.class, id);
    }

    @Override
    public VatLieuEntity findUsingName(String name) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery("from VatLieuEntity where name = :name", VatLieuEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void save(VatLieuEntity vatLieuEntity) {
        entityManager.persist(vatLieuEntity);
    }

    @Override
    public void deleteById(int id) {
        VatLieuEntity vatLieuEntity = entityManager.find(VatLieuEntity.class, id);
        entityManager.remove(vatLieuEntity);
    }

    @Override
    public void update(VatLieuEntity vatLieuEntity) {
        entityManager.merge(vatLieuEntity);
    }

    @Override
    public List<VatLieuEntity> findAllAndJoinFetch() {
        return null;
    }

    @Override
    public VatLieuEntity findByIdAndJoinFetch(int id) {
        return null;
    }

    @Override
    public List<VatLieuEntity> searchByHangMuc(int id) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery("from VatLieuEntity vl where vl.hangMuc.id = :id order by vl.id", VatLieuEntity.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
