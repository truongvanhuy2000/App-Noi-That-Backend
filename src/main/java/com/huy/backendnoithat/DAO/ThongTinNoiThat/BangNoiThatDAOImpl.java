package com.huy.backendnoithat.DAO.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
public class BangNoiThatDAOImpl implements BangNoiThatDAO{
    EntityManager entityManager;
    @Autowired
    public BangNoiThatDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<PhongCachNoiThatEntity> fetchAll() {

    }
}
