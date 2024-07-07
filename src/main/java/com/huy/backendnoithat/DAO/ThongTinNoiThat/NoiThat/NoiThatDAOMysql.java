package com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.Constant.AccountConstant;
import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;
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

    @Override
    public List<NoiThatEntity> searchBy(String owner, String phongCachName) {
        TypedQuery<NoiThatEntity> query = entityManager.createQuery(
                "from NoiThatEntity nt " +
                        "where nt.phongCachNoiThatEntity.name = :phongCachName and nt.account.username = :owner " +
                        "order by nt.id", NoiThatEntity.class);
        query.setParameter("owner", owner);
        query.setParameter("phongCachName", phongCachName);
        return query.getResultList();
    }
    @Transactional
    @Override
    public void copySampleDataFromAdmin(int id, int parentId, String parentName) {
        String jpql = "INSERT INTO noithat (name, account_id, phong_cach_id) " +
                "SELECT nt.name, :id, :parentId " +
                "FROM noithat nt " +
                "JOIN phongcachnoithat pc ON pc.id = nt.phong_cach_id " +
                "WHERE nt.account_id = :adminId and pc.name = :parentName";
        Query query = entityManager.createNativeQuery(jpql);
        query.setParameter("id", id);
        query.setParameter("parentId", parentId);
        query.setParameter("parentName", parentName);
        query.setParameter("adminId", AccountConstant.ADMIN_ID);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void swap(int accountId, int id1, int id2) {
        String jpql = "update noithat pc1, noithat pc2 " +
                "LEFT JOIN appnoithat.hangmuc n1 ON n1.noi_that_id = :id1 " +
                "LEFT JOIN appnoithat.hangmuc n2 ON n2.noi_that_id = :id2 " +
                "set pc1.name = pc2.name, " +
                "pc2.name = pc1.name, " +
                "n1.noi_that_id = :id2, " +
                "n2.noi_that_id = :id1 " +
                "where pc1.id = :id1 and pc2.id = :id2";
        Query query = entityManager.createNativeQuery(jpql);
        query.setParameter("id1", id1);
        query.setParameter("id2", id2);
        query.executeUpdate();
    }

    @Transactional
    public void exchange(int id1, int id2, String owner) {

    }
}
