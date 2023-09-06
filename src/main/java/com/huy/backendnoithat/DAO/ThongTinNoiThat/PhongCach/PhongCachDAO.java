package com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhongCachDAO{
    List<PhongCachNoiThatEntity> findAll();
    PhongCachNoiThatEntity findById(int id);
    PhongCachNoiThatEntity findUsingName(String name);
    void save(PhongCachNoiThatEntity phongCachNoiThatEntity);
    void deleteById(int id);
    void update(PhongCachNoiThatEntity phongCachNoiThatEntity);
    List<PhongCachNoiThatEntity> findAllAndJoinFetch();
    PhongCachNoiThatEntity findByIdAndJoinFetch(int id);
}
