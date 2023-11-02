package com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.Constant.AccountConstant;
import com.huy.backendnoithat.Entity.BangNoiThat.HangMucEntity;
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

    @Override
    public List<HangMucEntity> searchBy(String owner, String phongCachName, String noiThatName) {
        TypedQuery<HangMucEntity> query = entityManager.createQuery(
                "from HangMucEntity pc " +
                        "where pc.noiThatEntity.name = :noiThatName " +
                        "and pc.noiThatEntity.phongCachNoiThatEntity.name = :phongCachName " +
                        "and pc.account.username = :owner " +
                        "order by pc.id", HangMucEntity.class);
        query.setParameter("phongCachName", phongCachName);
        query.setParameter("noiThatName", noiThatName);
        query.setParameter("owner", owner);
        return query.getResultList();
    }
    @Transactional
    @Override
    public void copySampleDataFromAdmin(int id, int parentId, String noithatName, String phongcachName) {
        String jpql = "INSERT INTO hangmuc (name, account_id, noi_that_id) " +
                "SELECT hm.name, :id, :parentId" +
                " FROM hangmuc hm " +
                "JOIN noithat nt ON nt.id = hm.noi_that_id " +
                "JOIN phongcachnoithat pc on nt.phong_cach_id = pc.id " +
                "WHERE hm.account_id = :adminId and nt.name = :noithatName and pc.name = :phongcachName";
        Query query = entityManager.createNativeQuery(jpql);
        query.setParameter("id", id);
        query.setParameter("parentId", parentId);
        query.setParameter("noithatName", noithatName);
        query.setParameter("phongcachName", phongcachName);
        query.setParameter("adminId", AccountConstant.ADMIN_ID);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void swap(int accountId, int id1, int id2) {
        String jpql = "update appnoithat.hangmuc pc1, appnoithat.hangmuc pc2 " +
                "LEFT JOIN appnoithat.vatlieu n1 ON n1.hang_muc_id = :id1 " +
                "LEFT JOIN appnoithat.vatlieu n2 ON n2.hang_muc_id = :id2 " +
                "set pc1.name = pc2.name, " +
                "pc2.name = pc1.name, " +
                "n1.hang_muc_id = :id2, " +
                "n2.hang_muc_id = :id1 " +
                "where pc1.id = :id1 and pc2.id = :id2";
        Query query = entityManager.createNativeQuery(jpql);
        query.setParameter("id1", id1);
        query.setParameter("id2", id2);
        query.executeUpdate();
    }


}
