package com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.DTO.BangNoiThat.ThongSo;

import java.util.List;

public interface ThongSoService {
    List<ThongSo> findAll(String owner);
    ThongSo findUsingId(String owner, int id);
    ThongSo findUsingName(String owner, String name);
    void save(String owner, ThongSo thongSo, int parentId);
    void deleteById(String owner, int id);
    void update(String owner, ThongSo thongSo);
    List<ThongSo> searchByVatLieu(String owner, int id);

    void copySampleDataFromAdmin(String token, int parentId);
}
