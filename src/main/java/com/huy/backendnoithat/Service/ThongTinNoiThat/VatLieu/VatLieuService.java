package com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;

import java.util.List;

public interface VatLieuService {
    // Vat Lieu
    List<VatLieu> findAll();
    VatLieu findUsingId(int id);
    VatLieu findUsingName(String name);
    void save(VatLieu vatLieu, int parentId);
    void deleteById(int id);
    void update(VatLieu vatLieu);
    List<VatLieu> joinFetchVatLieu();
    List<VatLieu> searchByHangMuc(int id);
}
