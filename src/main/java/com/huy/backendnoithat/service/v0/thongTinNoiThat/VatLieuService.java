package com.huy.backendnoithat.service.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.VatLieu;

import java.util.List;

@Deprecated
public interface VatLieuService {
    // Vat Lieu
    List<VatLieu> findAll(String token);

    VatLieu findUsingId(String token, int id);

    VatLieu findUsingName(String token, String name);

    void save(String token, VatLieu vatLieu, int parentId);

    void deleteById(String token, int id);

    void update(String token, VatLieu vatLieu);

    List<VatLieu> searchByHangMuc(String token, int id);

    List<VatLieu> searchBy(String token, String phongCachName, String noiThatName, String hangMucName);

    void copySampleDataFromAdmin(String token, int parentId);

    void copySampleDataFromAdmin(int destinationId, int parentId, String hangMucName,
                                 String noiThatName, String phongCachName);

    void swap(String token, int id1, int id2);
}
