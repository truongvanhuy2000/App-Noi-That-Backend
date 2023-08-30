package com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.Entity.HangMucEntity;
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
    public List<HangMucEntity> findAll() {
        TypedQuery<HangMucEntity> query = entityManager.createQuery("from HangMucEntity pc ORDER BY pc.id", HangMucEntity.class);
        return query.getResultList();
    }
    @Override
    public HangMucEntity findById(int id) {
        return entityManager.find(HangMucEntity.class, id);
    }
    @Override
    public HangMucEntity findUsingName(String name) {
        TypedQuery<HangMucEntity> query = entityManager.createQuery("from HangMucEntity where name = :name", HangMucEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
    @Override
    public void save(HangMucEntity hangMucEntity) {
        entityManager.persist(hangMucEntity);
    }
    @Override
    public void deleteById(int id) {
        HangMucEntity hangMucEntity = entityManager.find(HangMucEntity.class, id);
        entityManager.remove(hangMucEntity);
    }
    @Override
    public void update(HangMucEntity hangMucEntity) {
        entityManager.merge(hangMucEntity);
    }
    @Override
    public List<HangMucEntity> findAllAndJoinFetch() {
        return null;
    }
    @Override
    public HangMucEntity findByIdAndJoinFetch(int id) {
        return null;
    }

    @Override
    public List<HangMucEntity> searchByNoiThat(int id) {
        TypedQuery<HangMucEntity> query = entityManager.createQuery("from HangMucEntity pc where pc.noiThat.id = :id order by pc.id", HangMucEntity.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
