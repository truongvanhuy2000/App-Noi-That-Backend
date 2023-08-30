package com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.Entity.NoiThatEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("noiThatDAOMysql")
public class NoiThatDAOMysql implements NoiThatDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<NoiThatEntity> findAll() {
        TypedQuery<NoiThatEntity> query = entityManager.createQuery("from NoiThatEntity pc order by pc.id", NoiThatEntity.class);
        return query.getResultList();
    }

    @Override
    public NoiThatEntity findById(int id) {
        return entityManager.find(NoiThatEntity.class, id);
    }

    @Override
    public NoiThatEntity findUsingName(String name) {
        TypedQuery<NoiThatEntity> query = entityManager.createQuery("from NoiThatEntity where name = :name", NoiThatEntity.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void save(NoiThatEntity noiThatEntity) {
        entityManager.persist(noiThatEntity);
    }

    @Override
    public void deleteById(int id) {
        NoiThatEntity noiThatEntity = entityManager.find(NoiThatEntity.class, id);
        entityManager.remove(noiThatEntity);
    }

    @Override
    public void update(NoiThatEntity noiThatEntity) {
        entityManager.merge(noiThatEntity);
    }

    @Override
    public List<NoiThatEntity> findAllAndJoinFetch() {
        return null;
    }

    @Override
    public NoiThatEntity findByIdAndJoinFetch(int id) {
        return null;
    }

    @Override
    public List<NoiThatEntity> searchByPhongCach(int id) {
        TypedQuery<NoiThatEntity> query = entityManager.createQuery("from NoiThatEntity pc where pc.phongCachNoiThat.id = :id order by pc.id", NoiThatEntity.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
