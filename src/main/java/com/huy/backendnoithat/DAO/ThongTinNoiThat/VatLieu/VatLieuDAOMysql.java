package com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Constant.AccountConstant;
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
    public List<VatLieuEntity> findAll(String owner) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery(
                "from VatLieuEntity vl " +
                        "left join fetch vl.thongSoEntity " +
                        "where vl.account.username = :owner " +
                        "order by vl.id", VatLieuEntity.class);
        return query.getResultList();
    }

    @Override
    public VatLieuEntity findById(String owner, int id) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery(
                "from VatLieuEntity vl " +
                        "left join fetch vl.thongSoEntity " +
                        "where vl.account.username = :owner " +
                        "and vl.id = :id", VatLieuEntity.class);
        query.setParameter("owner", owner);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public VatLieuEntity findUsingName(String owner, String name) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery(
                "from VatLieuEntity pc " +
                        "left join fetch pc.thongSoEntity " +
                        "where pc.name = :name " +
                        "and pc.account.username = :owner", VatLieuEntity.class);
        query.setParameter("name", name);
        query.setParameter("owner", owner);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void save(String owner, VatLieuEntity vatLieuEntity, int parentId) {
        Query query = entityManager.createQuery(
                "insert into VatLieuEntity (name, account, hangMucEntity) " +
                        "select :name, " +
                        "(from AccountEntity a where a.username = :owner), " +
                        "(from HangMucEntity hm where hm.id = :parentId)");
        query.setParameter("name", vatLieuEntity.getName());
        query.setParameter("owner", owner);
        query.setParameter("parentId", parentId);
        query.executeUpdate();
    }

    @Override
    @Transactional
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
    @Transactional
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
                        "left join fetch pc.thongSoEntity " +
                        "where pc.hangMucEntity.id = :id and pc.account.username = :owner " +
                        "order by pc.id", VatLieuEntity.class);
        query.setParameter("id", id);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public List<VatLieuEntity> searchBy(String owner, String phongCachName, String noiThatName, String hangMucName) {
        TypedQuery<VatLieuEntity> query = entityManager.createQuery(
                "from VatLieuEntity pc " +
                        "left join fetch pc.thongSoEntity " +
                        "where pc.hangMucEntity.noiThatEntity.name = :noiThatName " +
                        "and pc.hangMucEntity.noiThatEntity.phongCachNoiThatEntity.name = :phongCachName " +
                        "and pc.hangMucEntity.name = :hangMucName " +
                        "and pc.account.username = :owner " +
                        "order by pc.id", VatLieuEntity.class);
        query.setParameter("noiThatName", noiThatName);
        query.setParameter("phongCachName", phongCachName);
        query.setParameter("hangMucName", hangMucName);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void copySampleDataFromAdmin(int id, int parentId, String hangMucName, String noithatName, String phongcachName) {
        String jpql = "INSERT INTO vatlieu (name, account_id, hang_muc_id) " +
                "SELECT vl.name, :id, :parentId" +
                " FROM vatlieu vl " +
                "JOIN hangmuc hm on vl.hang_muc_id = hm.id " +
                "JOIN noithat nt ON nt.id = hm.noi_that_id " +
                "JOIN phongcachnoithat pc on nt.phong_cach_id = pc.id " +
                "WHERE hm.account_id = :adminId " +
                "and nt.name = :noithatName and pc.name = :phongcachName and hm.name = :hangMucName";
        Query query = entityManager.createNativeQuery(jpql);
        query.setParameter("id", id);
        query.setParameter("parentId", parentId);
        query.setParameter("noithatName", noithatName);
        query.setParameter("phongcachName", phongcachName);
        query.setParameter("hangMucName", hangMucName);
        query.setParameter("adminId", AccountConstant.ADMIN_ID);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void swap(int id, int id1, int id2) {
        String jpql = "update appnoithat.vatlieu pc1, appnoithat.vatlieu pc2 " +
                "LEFT JOIN appnoithat.thongso n1 ON n1.vatlieu_id = :id1 " +
                "LEFT JOIN appnoithat.thongso n2 ON n2.vatlieu_id = :id2 " +
                "set pc1.name = pc2.name, " +
                "pc2.name = pc1.name, " +
                "n1.vatlieu_id = :id2, " +
                "n2.vatlieu_id = :id1 " +
                "where pc1.id = :id1 and pc2.id = :id2";
        Query query = entityManager.createNativeQuery(jpql);
        query.setParameter("id1", id1);
        query.setParameter("id2", id2);
        query.executeUpdate();
    }
}
