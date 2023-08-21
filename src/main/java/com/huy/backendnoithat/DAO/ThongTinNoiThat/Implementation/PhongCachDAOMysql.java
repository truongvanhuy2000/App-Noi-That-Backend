package com.huy.backendnoithat.DAO.ThongTinNoiThat.Implementation;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.Interface.PhongCachDAO;
import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;
@Repository("mysql")
public class PhongCachDAOMysql implements PhongCachDAO {
    EntityManager entityManager;
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<PhongCachNoiThat> findAll() {
        TypedQuery<PhongCachNoiThat> query = entityManager.createQuery("from PhongCachNoiThat", PhongCachNoiThat.class);
        return query.getResultList();
    }
    @Override
    public PhongCachNoiThat findById(int id) {
        return entityManager.find(PhongCachNoiThat.class, id);
    }
    @Override
    public PhongCachNoiThat findUsingName(String name) {
        TypedQuery<PhongCachNoiThat> query = entityManager.createQuery("from PhongCachNoiThat where name = :name", PhongCachNoiThat.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
    @Override
    public void save(PhongCachNoiThat phongCachNoiThat) {
        entityManager.persist(phongCachNoiThat);
    }
    @Override
    public void deleteById(int id) {
        PhongCachNoiThat phongCachNoiThat = entityManager.find(PhongCachNoiThat.class, id);
        entityManager.remove(phongCachNoiThat);
    }
    @Override
    public void update(PhongCachNoiThat phongCachNoiThat) {
        entityManager.merge(phongCachNoiThat);
    }
    @Override
    public List<PhongCachNoiThat> findAllAndJoinFetch() {
        TypedQuery<PhongCachNoiThat> query = entityManager.createQuery(
                "FROM PhongCachNoiThat pc "
                        + "JOIN FETCH pc.noiThat "
                        + "ORDER BY pc.id "
                , PhongCachNoiThat.class);
        return query.getResultList();
    }
    @Override
    public List<PhongCachNoiThat> findByIdAndJoinFetch(int id) {
        return null;
    }
}
