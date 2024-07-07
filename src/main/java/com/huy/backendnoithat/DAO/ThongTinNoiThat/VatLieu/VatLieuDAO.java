package com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.Entity.BangNoiThat.VatLieuEntity;

import java.util.List;

public interface VatLieuDAO {
    List<VatLieuEntity> findAll(String owner);
    VatLieuEntity findById(String owner, int id);
    VatLieuEntity findUsingName(String owner, String name);
    void save(String owner, VatLieuEntity vatLieuEntity, int parentId);
    void deleteById(String owner, int id);
    void update(String owner, VatLieuEntity vatLieuEntity);
    List<VatLieuEntity> findAllAndJoinFetch(String owner);
    VatLieuEntity findByIdAndJoinFetch(String owner, int id);
    List<VatLieuEntity> searchByHangMuc(String owner, int id);
    List<VatLieuEntity> searchBy(String owner, String phongCachName, String noiThatName, String hangMucName);
    public void copySampleDataFromAdmin(int id, int parentId, String hangMucName, String noithatName, String phongcachName);

    void swap(int id, int id1, int id2);
}
