package com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;

import java.util.List;

public interface VatLieuService {
    // Vat Lieu
    List<VatLieu> findAll(String owner);
    VatLieu findUsingId(String owner, int id);
    VatLieu findUsingName(String owner, String name);
    void save(String owner, VatLieu vatLieu, int parentId);
    void deleteById(String owner, int id);
    void update(String owner, VatLieu vatLieu);
    List<VatLieu> joinFetchVatLieu();
    List<VatLieu> searchByHangMuc(String owner, int id);

    List<VatLieu> searchBy(String owner, String phongCachName, String noiThatName, String hangMucName);
}
