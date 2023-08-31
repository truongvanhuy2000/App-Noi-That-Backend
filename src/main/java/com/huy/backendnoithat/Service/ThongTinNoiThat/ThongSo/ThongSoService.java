package com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.DTO.BangNoiThat.ThongSo;

import java.util.List;

public interface ThongSoService {
    List<ThongSo> findAll();
    ThongSo findUsingId(int id);
    ThongSo findUsingName(String name);
    void save(ThongSo thongSo, int parentId);
    void deleteById(int id);
    void update(ThongSo thongSo);
    List<ThongSo> searchByVatLieu(int id);
}
