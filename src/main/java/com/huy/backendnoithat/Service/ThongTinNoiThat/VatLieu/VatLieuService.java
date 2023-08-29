package com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Entity.VatLieu;
import com.huy.backendnoithat.Response.VatLieuResponse;

import java.util.List;

public interface VatLieuService {
    // Vat Lieu
    List<VatLieuResponse> findAll();
    VatLieuResponse findUsingId(int id);
    VatLieuResponse findUsingName(String name);
    void save(VatLieu vatLieu);
    void deleteById(int id);
    void update(VatLieu vatLieu);
}
