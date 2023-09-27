package com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository("noiThatDAOMysql")
public class NoiThatDAOMysql implements NoiThatDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<NoiThatEntity> findAll(String owner) {
        TypedQuery<NoiThatEntity> query = entityManager.createQuery(
                "from NoiThatEntity pc " +
                        "where pc.account.username = :owner " +
                        "order by pc.id", NoiThatEntity.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public NoiThatEntity findById(String owner, int id) {
        TypedQuery<NoiThatEntity> query = entityManager.createQuery(
                "from NoiThatEntity pc " +
                        "where pc.account.username = :owner " +
                        "and pc.id = :id", NoiThatEntity.class);
        query.setParameter("owner", owner);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public NoiThatEntity findUsingName(String owner, String name) {
        TypedQuery<NoiThatEntity> query = entityManager.createQuery(
                "from NoiThatEntity pc " +
                        "where pc.name = :name " +
                        "and pc.account.username = :owner", NoiThatEntity.class);
        query.setParameter("name", name);
        query.setParameter("owner", owner);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void save(String owner, NoiThatEntity noiThatEntity, int parentId) {
        Query query = entityManager.createQuery(
                "insert into NoiThatEntity (name, account, phongCachNoiThatEntity) " +
                        "select :name, " +
                        "(from AccountEntity a where a.username = :owner), " +
                        "(from PhongCachNoiThatEntity pc where pc.id = :parentId)");
        query.setParameter("name", noiThatEntity.getName());
        query.setParameter("owner", owner);
        query.setParameter("parentId", parentId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteById(String owner, int id) {
        Query query = entityManager.createQuery(
                "delete from NoiThatEntity pc " +
                        "where pc.id = :id " +
                        "and pc.account.id = (select a.id from AccountEntity a where a.username = :owner)");
        query.setParameter("id", id);
        query.setParameter("owner", owner);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void update(String owner, NoiThatEntity noiThatEntity) {
        Query query = entityManager.createQuery(
                "update NoiThatEntity pc" +
                        " set pc.name = :name" +
                        " where pc.id = :id " +
                        "and pc.account.id = (select a.id from AccountEntity a where a.username = :owner)");
        query.setParameter("name", noiThatEntity.getName());
        query.setParameter("id", noiThatEntity.getId());
        query.setParameter("owner", owner);
        query.executeUpdate();
    }

    @Override
    public List<NoiThatEntity> findAllAndJoinFetch() {
        TypedQuery<NoiThatEntity> query = entityManager.createQuery(
                "FROM NoiThatEntity nt " +
                        "JOIN FETCH nt.hangMucEntity hm "
                , NoiThatEntity.class);
        return query.getResultList();
    }

    @Override
    public NoiThatEntity findByIdAndJoinFetch(int id) {
        return null;
    }

    @Override
    public List<NoiThatEntity> searchByPhongCach(String owner, int id) {
        TypedQuery<NoiThatEntity> query = entityManager.createQuery(
                "from NoiThatEntity pc " +
                        "where pc.phongCachNoiThatEntity.id = :id and pc.account.username = :owner " +
                        "order by pc.id", NoiThatEntity.class);
        query.setParameter("id", id);
        query.setParameter("owner", owner);
        return query.getResultList();
    }
}
