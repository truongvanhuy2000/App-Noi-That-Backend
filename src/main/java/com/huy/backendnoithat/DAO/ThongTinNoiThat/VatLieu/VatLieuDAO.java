package com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Entity.ThongSo;
import com.huy.backendnoithat.Entity.VatLieu;

import java.util.List;

public interface VatLieuDAO {
    List<VatLieu> findAll();
    VatLieu findById(int id);
    VatLieu findUsingName(String name);
    void save(VatLieu vatLieu);
    void deleteById(int id);
    void update(VatLieu vatLieu);
    List<VatLieu> findAllAndJoinFetch();
    VatLieu findByIdAndJoinFetch(int id);
}
