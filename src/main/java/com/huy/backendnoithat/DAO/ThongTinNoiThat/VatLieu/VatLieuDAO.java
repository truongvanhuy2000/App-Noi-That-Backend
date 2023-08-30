package com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Entity.VatLieuEntity;

import java.util.List;

public interface VatLieuDAO {
    List<VatLieuEntity> findAll();
    VatLieuEntity findById(int id);
    VatLieuEntity findUsingName(String name);
    void save(VatLieuEntity vatLieuEntity);
    void deleteById(int id);
    void update(VatLieuEntity vatLieuEntity);
    List<VatLieuEntity> findAllAndJoinFetch();
    VatLieuEntity findByIdAndJoinFetch(int id);
    List<VatLieuEntity> searchByHangMuc(int id);
}
