package com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.Entity.HangMuc;
import com.huy.backendnoithat.Entity.NoiThat;
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
    public List<NoiThat> findAll() {
        TypedQuery<NoiThat> query = entityManager.createQuery("from NoiThat pc order by pc.id", NoiThat.class);
        return query.getResultList();
    }

    @Override
    public NoiThat findById(int id) {
        return entityManager.find(NoiThat.class, id);
    }

    @Override
    public NoiThat findUsingName(String name) {
        TypedQuery<NoiThat> query = entityManager.createQuery("from NoiThat where name = :name", NoiThat.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public void save(NoiThat noiThat) {
        entityManager.persist(noiThat);
    }

    @Override
    public void deleteById(int id) {
        NoiThat noiThat = entityManager.find(NoiThat.class, id);
        entityManager.remove(noiThat);
    }

    @Override
    public void update(NoiThat noiThat) {
        entityManager.merge(noiThat);
    }

    @Override
    public List<NoiThat> findAllAndJoinFetch() {
        return null;
    }

    @Override
    public NoiThat findByIdAndJoinFetch(int id) {
        return null;
    }
}
