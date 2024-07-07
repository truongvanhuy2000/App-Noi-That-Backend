package com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;

import java.util.List;

public interface PhongCachDAO{
    List<PhongCachNoiThatEntity> findAll(String owner);
    PhongCachNoiThatEntity findById(String owner, int id);
    PhongCachNoiThatEntity findUsingName(String owner, String name);
    void save(String owner, PhongCachNoiThatEntity phongCachNoiThatEntity);
    void deleteById(String owner, int id);
    void update(String owner, PhongCachNoiThatEntity phongCachNoiThatEntity);
    List<PhongCachNoiThatEntity> findAllAndJoinFetch(String owner);
    PhongCachNoiThatEntity findByIdAndJoinFetch(String owner, int id);
    void copySampleDataFromAdmin(int id);

    void swap(int id, int id1, int id2);
}
