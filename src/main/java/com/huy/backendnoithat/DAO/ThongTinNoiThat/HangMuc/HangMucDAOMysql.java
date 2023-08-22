package com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.Entity.HangMuc;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("hangMucDAOMysql")
public class HangMucDAOMysql implements HangMucDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<HangMuc> findAll() {
        return null;
    }
    @Override
    public HangMuc findById(int id) {
        return null;
    }
    @Override
    public HangMuc findUsingName(String name) {
        return null;
    }
    @Override
    public void save(HangMuc hangMuc) {

    }
    @Override
    public void deleteById(int id) {

    }
    @Override
    public void update(HangMuc hangMuc) {

    }
    @Override
    public List<HangMuc> findAllAndJoinFetch() {
        return null;
    }
    @Override
    public HangMuc findByIdAndJoinFetch(int id) {
        return null;
    }
}
