package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;

import java.util.List;

public interface PhongCachService {
    // Phong Cach Noi That
    List<PhongCach> findAll(String owner);
    PhongCach findById(String owner, int id);
    PhongCach findUsingName(String owner, String name);
    void save(String owner, PhongCach phongCach);
    void deleteById(String owner, int id);
    void update(String owner, PhongCach phongCach);
    List<PhongCach> joinFetchPhongCach(String owner);
    PhongCach joinFetchPhongCachUsingId(String owner, int id);

    void copySampleDataFromAdmin(String token);
}
