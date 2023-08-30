package com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach;

import com.huy.backendnoithat.Entity.PhongCachNoiThatEntity;
import com.huy.backendnoithat.DataModel.PhongCach;

import java.util.List;

public interface PhongCachService {
    // Phong Cach Noi That
    List<PhongCach> findAll();
    PhongCach findById(int id);
    PhongCach findUsingName(String name);
    void save(PhongCach phongCachNoiThatEntity);
    void deleteById(int id);
    void update(PhongCach phongCachNoiThatEntity);
    List<PhongCach> joinFetchPhongCach();
    PhongCach joinFetchPhongCachUsingId(int id);
}
