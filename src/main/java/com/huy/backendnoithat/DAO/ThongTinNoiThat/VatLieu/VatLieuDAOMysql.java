package com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.VatLieuEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        TypedQuery<VatLieuEntity> query = entityManager.createQuery("from VatLieuEntity vl join fetch vl.thongSoEntity order by vl.id", VatLieuEntity.class);
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
    @Transactional
    public void save(VatLieuEntity vatLieuEntity) {
        entityManager.persist(vatLieuEntity);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        VatLieuEntity vatLieuEntity = entityManager.find(VatLieuEntity.class, id);
        entityManager.remove(vatLieuEntity);
    }
    @Override
    @Transactional
    public void update(VatLieuEntity vatLieuEntity) {
        String updateQuery = "update vatlieu set name = :name where id = :id";
        Query query = entityManager.createNativeQuery(updateQuery);
        query.setParameter("name", vatLieuEntity.getName());
        query.setParameter("id", vatLieuEntity.getId());
        query.executeUpdate();

    }
    @Override
    public List<VatLieuEntity> findAllAndJoinFetch() {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery(
                "FROM VatLieuEntity vl " +
                        "JOIN FETCH vl.thongSoEntity ts "
                , VatLieuEntity.class);
        return query.getResultList();
    }
    @Override
    public VatLieuEntity findByIdAndJoinFetch(int id) {
        return null;
    }
    @Override
    public List<VatLieuEntity> searchByHangMuc(int id) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery("from VatLieuEntity vl where vl.hangMucEntity.id = :id order by vl.id", VatLieuEntity.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<VatLieuEntity> findAll(String owner) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery(
                "from VatLieuEntity vl " +
                        "join fetch vl.thongSoEntity " +
                        "where vl.account.username = :owner " +
                        "order by vl.id", VatLieuEntity.class);
        return query.getResultList();
    }

    @Override
    public VatLieuEntity findById(String owner, int id) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery(
                "from VatLieuEntity pc " +
                        "where pc.account.username = :owner " +
                        "and pc.id = :id", VatLieuEntity.class);
        query.setParameter("owner", owner);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public VatLieuEntity findUsingName(String owner, String name) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery(
                "from VatLieuEntity pc " +
                        "where pc.name = :name " +
                        "and pc.account.username = :owner", VatLieuEntity.class);
        query.setParameter("name", name);
        query.setParameter("owner", owner);
        return query.getSingleResult();
    }

    @Override
    public void save(String owner, VatLieuEntity vatLieuEntity) {
        Query query = entityManager.createQuery(
                "insert into VatLieuEntity (name, account) " +
                        "select :name, a " +
                        "from AccountEntity a " +
                        "where a.username = :owner");
        query.setParameter("name", vatLieuEntity.getName());
        query.setParameter("owner", owner);
        query.executeUpdate();
    }

    @Override
    public void deleteById(String owner, int id) {
        Query query = entityManager.createQuery(
                "delete from VatLieuEntity pc " +
                        "where pc.id = :id " +
                        "and pc.account.id = (select a.id from AccountEntity a where a.username = :owner)");
        query.setParameter("id", id);
        query.setParameter("owner", owner);
        query.executeUpdate();
    }

    @Override
    public void update(String owner, VatLieuEntity vatLieuEntity) {
        Query query = entityManager.createQuery(
                "update VatLieuEntity pc" +
                        " set pc.name = :name" +
                        " where pc.id = :id " +
                        "and pc.account.id = (select a.id from AccountEntity a where a.username = :owner)");
        query.setParameter("name", vatLieuEntity.getName());
        query.setParameter("id", vatLieuEntity.getId());
        query.setParameter("owner", owner);
        query.executeUpdate();
    }

    @Override
    public List<VatLieuEntity> findAllAndJoinFetch(String owner) {
        return null;
    }

    @Override
    public VatLieuEntity findByIdAndJoinFetch(String owner, int id) {
        return null;
    }

    @Override
    public List<VatLieuEntity> searchByHangMuc(String owner, int id) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery(
                "from VatLieuEntity pc " +
                        "where pc.phongCachNoiThatEntity.id = :id and pc.account.username = :owner " +
                        "order by pc.id", VatLieuEntity.class);
        query.setParameter("id", id);
        query.setParameter("owner", owner);
        return query.getResultList();
    }
}
