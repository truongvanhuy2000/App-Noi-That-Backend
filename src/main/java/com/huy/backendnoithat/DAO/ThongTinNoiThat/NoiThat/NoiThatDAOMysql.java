package com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.Entity.NoiThat;
import jakarta.persistence.EntityManager;
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
        return null;
    }

    @Override
    public NoiThat findById(int id) {
        return null;
    }

    @Override
    public NoiThat findUsingName(String name) {
        return null;
    }

    @Override
    public void save(NoiThat noiThat) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(NoiThat noiThat) {

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
