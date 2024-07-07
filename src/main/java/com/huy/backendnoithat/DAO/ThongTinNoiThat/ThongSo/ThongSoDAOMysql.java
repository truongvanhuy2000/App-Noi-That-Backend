package com.huy.backendnoithat.DAO.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.Constant.AccountConstant;
import com.huy.backendnoithat.Entity.BangNoiThat.ThongSoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository("thongSoDAOMysql")
public class ThongSoDAOMysql implements ThongSoDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ThongSoEntity> findAll(String owner) {
        TypedQuery<ThongSoEntity> query = entityManager.createQuery(
                "from ThongSoEntity pc " +
                        "where pc.account.username = :owner " +
                        "order by pc.id", ThongSoEntity.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public ThongSoEntity findById(String owner, int id) {
        TypedQuery<ThongSoEntity> query = entityManager.createQuery(
                "from ThongSoEntity pc " +
                        "where pc.account.username = :owner " +
                        "and pc.id = :id", ThongSoEntity.class);
        query.setParameter("owner", owner);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public ThongSoEntity findUsingName(String owner, String name) {
        return null;
    }

    @Override
    @Transactional
    public void save(String owner, ThongSoEntity thongSoEntity, int parentId) {
        Query query = entityManager.createQuery(
                "insert into ThongSoEntity (dai, rong, cao, donVi, donGia, account, vatLieuEntity) " +
                        "select :dai, :rong, :cao, :don_vi, :don_gia, " +
                        "(from AccountEntity a where a.username = :owner), " +
                        "(from VatLieuEntity nt where nt.id = :parentId)");
        query.setParameter("dai", thongSoEntity.getDai());
        query.setParameter("rong", thongSoEntity.getRong());
        query.setParameter("cao", thongSoEntity.getCao());
        query.setParameter("don_vi", thongSoEntity.getDonVi());
        query.setParameter("don_gia", thongSoEntity.getDonGia());
        query.setParameter("owner", owner);
        query.setParameter("parentId", parentId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deleteById(String owner, int id) {
        Query query = entityManager.createQuery(
                "delete from ThongSoEntity pc " +
                        "where pc.id = :id " +
                        "and pc.account.id = (select a.id from AccountEntity a where a.username = :owner)");
        query.setParameter("id", id);
        query.setParameter("owner", owner);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void update(String owner, ThongSoEntity thongSoEntity) {
        Query query = entityManager.createQuery(
                "update ThongSoEntity pc" +
                        " set pc.dai = :dai, " +
                        " pc.rong = :rong, " +
                        " pc.cao = :cao, " +
                        " pc.donVi = :don_vi, " +
                        " pc.donGia = :don_gia " +
                        " where pc.id = :id " +
                        "and pc.account.id = (select a.id from AccountEntity a where a.username = :owner)");
        query.setParameter("dai", thongSoEntity.getDai());
        query.setParameter("rong", thongSoEntity.getRong());
        query.setParameter("cao", thongSoEntity.getCao());
        query.setParameter("don_vi", thongSoEntity.getDonVi());
        query.setParameter("don_gia", thongSoEntity.getDonGia());
        query.setParameter("id", thongSoEntity.getId());
        query.setParameter("owner", owner);
        query.executeUpdate();
    }

    @Override
    public List<ThongSoEntity> findAllAndJoinFetch() {
        return null;
    }
    @Override
    public ThongSoEntity findByIdAndJoinFetch(int id) {
        return null;
    }

    @Override
    public List<ThongSoEntity> searchByVatLieu(String owner, int id) {
        TypedQuery<ThongSoEntity> query = entityManager.createQuery(
                "from ThongSoEntity pc " +
                        "where pc.vatLieuEntity.id = :id and pc.account.username = :owner", ThongSoEntity.class);
        query.setParameter("id", id);
        query.setParameter("owner", owner);
        return query.getResultList();
    }
    @Transactional
    @Override
    public void copySampleDataFromAdmin(int accountId, int parentId, String vatLieuName,
                                        String hangMucName, String noithatName, String phongcachName) {
        String jpql = "INSERT INTO thongso (dai, rong, cao, don_vi, don_gia, account_id, vatlieu_id) " +
                "SELECT ts.dai, ts.rong, ts.cao, ts.don_vi, ts.don_gia, :accountId, :parentId" +
                " FROM thongso ts " +
                "JOIN vatlieu vl on ts.vatlieu_id = vl.id " +
                "JOIN hangmuc hm on vl.hang_muc_id = hm.id " +
                "JOIN noithat nt ON nt.id = hm.noi_that_id " +
                "JOIN phongcachnoithat pc on nt.phong_cach_id = pc.id " +
                "WHERE hm.account_id = :adminId " +
                "and nt.name = :noithatName and pc.name = :phongcachName and hm.name = :hangMucName and vl.name = :vatLieuName";
        Query query = entityManager.createNativeQuery(jpql);
        query.setParameter("accountId", accountId);
        query.setParameter("parentId", parentId);
        query.setParameter("noithatName", noithatName);
        query.setParameter("phongcachName", phongcachName);
        query.setParameter("hangMucName", hangMucName);
        query.setParameter("vatLieuName", vatLieuName);
        query.setParameter("adminId", AccountConstant.ADMIN_ID);
        query.executeUpdate();
    }
}

