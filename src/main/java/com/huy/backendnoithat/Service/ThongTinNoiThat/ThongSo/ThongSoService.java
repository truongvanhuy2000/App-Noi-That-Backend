package com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.DTO.BangNoiThat.ThongSo;

import java.util.List;

public interface ThongSoService {
    List<ThongSo> findAll(String token);
    ThongSo findUsingId(String token, int id);
    ThongSo findUsingName(String token, String name);
    void save(String token, ThongSo thongSo, int parentId);
    void deleteById(String token, int id);
    void update(String token, ThongSo thongSo);
    List<ThongSo> searchByVatLieu(String token, int id);

    void copySampleDataFromAdmin(String token, int parentId);

    void copySampleDataFromAdmin(int destinationId, int parentId, String vatLieuName,
                                 String hangMucName, String noiThatName, String phongCachName);
}
