package com.huy.backendnoithat.DAO.ThongTinNoiThat.Implementation;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.Interface.PhongCachDAO;
import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("mysql")
public class PhongCachDAOMysql implements PhongCachDAO {
    EntityManager entityManager;
    @Autowired
    public PhongCachDAOMysql(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<PhongCachNoiThat> findAll() {
        return null;
    }

    @Override
    public PhongCachNoiThat findById(int id) {
        return null;
    }

    @Override
    public PhongCachNoiThat findUsingName(String name) {
        return null;
    }

    @Override
    public void save(PhongCachNoiThat phongCachNoiThat) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(PhongCachNoiThat phongCachNoiThat) {

    }
}
