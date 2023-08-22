package com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Entity.VatLieu;

import java.util.List;

public interface VatLieuService {
    // Vat Lieu
    List<VatLieu> findAll();
    VatLieu findUsingId(int id);
    VatLieu findUsingName(String name);
    void save(VatLieu vatLieu);
    void deleteById(int id);
    void update(VatLieu vatLieu);
}
