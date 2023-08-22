package com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Entity.VatLieu;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("vatLieuDAOMysql")
public class VatLieuDAOMysql implements VatLieuDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<VatLieu> findAll() {
        return null;
    }

    @Override
    public VatLieu findById(int id) {
        return null;
    }

    @Override
    public VatLieu findUsingName(String name) {
        return null;
    }

    @Override
    public void save(VatLieu vatLieu) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(VatLieu vatLieu) {

    }

    @Override
    public List<VatLieu> findAllAndJoinFetch() {
        return null;
    }

    @Override
    public VatLieu findByIdAndJoinFetch(int id) {
        return null;
    }
}
