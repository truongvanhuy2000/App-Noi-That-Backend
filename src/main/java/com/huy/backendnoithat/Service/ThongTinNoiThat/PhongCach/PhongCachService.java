package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import com.huy.backendnoithat.Response.PhongCachResponse;

import java.util.List;

public interface PhongCachService {
    // Phong Cach Noi That
    List<PhongCachResponse> findAll();
    PhongCachResponse findById(int id);
    PhongCachResponse findUsingName(String name);
    void save(PhongCachNoiThat phongCachNoiThat);
    void deleteById(int id);
    void update(PhongCachNoiThat phongCachNoiThat);
}
