package com.huy.backendnoithat.Service.ThongTinNoiThat.Interface;

import com.huy.backendnoithat.Entity.ThongSo;

import java.util.List;

public interface ThongSoService {
    List<ThongSo> findAll();
    ThongSo findUsingId(int id);
    ThongSo findUsingName(String name);
    void save(ThongSo thongSo);
    void deleteById(int id);
    void update(ThongSo thongSo);
}
