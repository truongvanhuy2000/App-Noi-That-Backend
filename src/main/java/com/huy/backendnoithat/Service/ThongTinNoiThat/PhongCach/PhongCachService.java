package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.Entity.PhongCachNoiThat;

import java.util.List;

public interface PhongCachService {
    // Phong Cach Noi That
    List<PhongCachNoiThat> findAll();
    PhongCachNoiThat findById(int id);
    PhongCachNoiThat findUsingName(String name);
    void save(PhongCachNoiThat phongCachNoiThat);
    void deleteById(int id);
    void update(PhongCachNoiThat phongCachNoiThat);
}
