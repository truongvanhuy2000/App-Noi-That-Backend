package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;

import java.util.List;

public interface PhongCachService {
    // Phong Cach Noi That
    List<PhongCach> findAll(String token);
    PhongCach findById(String token, int id);
    PhongCach findUsingName(String token, String name);
    void save(String token, PhongCach phongCach);
    void deleteById(String token, int id);
    void update(String token, PhongCach phongCach);
    List<PhongCach> joinFetchPhongCach(String token);
    PhongCach joinFetchPhongCachUsingId(String token, int id);
    void copySampleDataFromAdmin(String token);
    void copySampleDataFromAdmin(int accountId);
    void swap(String token, int id1, int id2);
}
