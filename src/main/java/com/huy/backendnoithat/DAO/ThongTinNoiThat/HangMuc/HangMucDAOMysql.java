package com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.Entity.BangNoiThat.HangMucEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("hangMucDAOMysql")
public class HangMucDAOMysql implements HangMucDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<HangMucEntity> findAll(String owner) {
        TypedQuery<HangMucEntity> query = entityManager.createQuery(
                "from HangMucEntity pc where pc.account.username = :owner " +
                        "ORDER BY pc.id", HangMucEntity.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public HangMucEntity findById(String owner, int id) {
        TypedQuery<HangMucEntity> query = entityManager.createQuery(
                "from HangMucEntity pc " +
                        "where pc.account.username = :owner " +
                        "and pc.id = :id", HangMucEntity.class);
        query.setParameter("owner", owner);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public HangMucEntity findUsingName(String owner, String name) {
        TypedQuery<HangMucEntity> query = entityManager.createQuery(
                "from HangMucEntity pc " +
                        "where pc.name = :name " +
                        "and pc.account.username = :owner", HangMucEntity.class);
        query.setParameter("name", name);
        query.setParameter("owner", owner);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void save(String owner, HangMucEntity hangMucEntity, int parentId) {
        Query query = entityManager.createQuery(
                "insert into HangMucEntity (name, account, noiThatEntity) " +
                        "select :name, " +
                        "(from AccountEntity a where a.username = :owner), " +
                        "(from NoiThatEntity nt where nt.id = :parentId)");
        query.setParameter("name", hangMucEntity.getName());
        query.setParameter("owner", owner);
        query.setParameter("parentId", parentId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteById(String owner, int id) {
        Query query = entityManager.createQuery(
                "delete from HangMucEntity pc " +
                        "where pc.id = :id " +
                        "and pc.account.id = (select a.id from AccountEntity a where a.username = :owner)");
        query.setParameter("id", id);
        query.setParameter("owner", owner);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void update(String owner, HangMucEntity hangMucEntity) {
        Query query = entityManager.createQuery(
                "update HangMucEntity pc" +
                        " set pc.name = :name" +
                        " where pc.id = :id " +
                        "and pc.account.id = (select a.id from AccountEntity a where a.username = :owner)");
        query.setParameter("name", hangMucEntity.getName());
        query.setParameter("id", hangMucEntity.getId());
        query.setParameter("owner", owner);
        query.executeUpdate();
    }

    @Override
    public List<HangMucEntity> findAllAndJoinFetch(String owner) {
        return null;
    }

    @Override
    public HangMucEntity findByIdAndJoinFetch(String owner, int id) {
        return null;
    }

    @Override
    public List<HangMucEntity> searchByNoiThat(String owner, int id) {
        TypedQuery<HangMucEntity> query = entityManager.createQuery(
                "from HangMucEntity pc " +
                        "where pc.noiThatEntity.id = :id and pc.account.username = :owner " +
                        "order by pc.id", HangMucEntity.class);
        query.setParameter("id", id);
        query.setParameter("owner", owner);
        return query.getResultList();
    }
}
