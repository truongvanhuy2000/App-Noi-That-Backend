package com.huy.backendnoithat.service.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.PhongCach;

import java.util.List;

@Deprecated
public interface PhongCachService {
    // Phong Cach Noi That
    List<PhongCach> findAll(String token);

    PhongCach findById(String token, int id);

    PhongCach findUsingName(String token, String name);

    void save(String token, PhongCach phongCach);

    void deleteById(String token, int id);

    void update(String token, PhongCach phongCach);

    void copySampleDataFromAdmin(String token);

    void copySampleDataFromAdmin(int accountId);

    void swap(String token, int id1, int id2);
}
