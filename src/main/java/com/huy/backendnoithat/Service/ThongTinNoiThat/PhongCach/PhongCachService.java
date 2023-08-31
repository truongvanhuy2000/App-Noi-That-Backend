package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;

import java.util.List;

public interface PhongCachService {
    // Phong Cach Noi That
    List<PhongCach> findAll();
    PhongCach findById(int id);
    PhongCach findUsingName(String name);
    void save(PhongCach phongCach);
    void deleteById(int id);
    void update(PhongCach phongCach);
    List<PhongCach> joinFetchPhongCach();
    PhongCach joinFetchPhongCachUsingId(int id);
}
