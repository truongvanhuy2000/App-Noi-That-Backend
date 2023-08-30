package com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Entity.VatLieuEntity;
import com.huy.backendnoithat.DataModel.VatLieu;

import java.util.List;

public interface VatLieuService {
    // Vat Lieu
    List<VatLieu> findAll();
    VatLieu findUsingId(int id);
    VatLieu findUsingName(String name);
    void save(VatLieuEntity vatLieuEntity);
    void deleteById(int id);
    void update(VatLieuEntity vatLieuEntity);
    List<VatLieu> searchByHangMuc(int id);
}
