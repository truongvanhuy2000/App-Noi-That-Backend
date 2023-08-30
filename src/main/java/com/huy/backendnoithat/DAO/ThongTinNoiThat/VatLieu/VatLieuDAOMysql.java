package com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Entity.VatLieu;
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
    public List<VatLieu> findAll() {
        TypedQuery<VatLieu> query = entityManager.createQuery("from VatLieu vl order by vl.id", VatLieu.class);
        return query.getResultList();

    }
    @Override
    public VatLieu findById(int id) {
        return entityManager.find(VatLieu.class, id);
    }

    @Override
    public VatLieu findUsingName(String name) {
        TypedQuery<VatLieu> query = entityManager.createQuery("from VatLieu where name = :name", VatLieu.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void save(VatLieu vatLieu) {
        entityManager.persist(vatLieu);
    }

    @Override
    public void deleteById(int id) {
        VatLieu vatLieu = entityManager.find(VatLieu.class, id);
        entityManager.remove(vatLieu);
    }

    @Override
    public void update(VatLieu vatLieu) {
        entityManager.merge(vatLieu);
    }

    @Override
    public List<VatLieu> findAllAndJoinFetch() {
        return null;
    }

    @Override
    public VatLieu findByIdAndJoinFetch(int id) {
        return null;
    }
}
