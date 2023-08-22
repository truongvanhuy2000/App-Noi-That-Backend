package com.huy.backendnoithat.DAO.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.Entity.PhongCachNoiThat;

import java.util.List;

public interface PhongCachDAO {
    List<PhongCachNoiThat> findAll();
    PhongCachNoiThat findById(int id);
    PhongCachNoiThat findUsingName(String name);
    void save(PhongCachNoiThat phongCachNoiThat);
    void deleteById(int id);
    void update(PhongCachNoiThat phongCachNoiThat);
    List<PhongCachNoiThat> findAllAndJoinFetch();
    PhongCachNoiThat findByIdAndJoinFetch(int id);
}
