package com.huy.backendnoithat.DAO.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.Entity.BangNoiThat.ThongSoEntity;

import java.util.List;

public interface ThongSoDAO {
    List<ThongSoEntity> findAll(String owner);
    ThongSoEntity findById(String owner, int id);
    ThongSoEntity findUsingName(String owner, String name);
    void save(String owner, ThongSoEntity thongSoEntity, int parentId);
    void deleteById(String owner, int id);
    void update(String owner, ThongSoEntity thongSoEntity);
    List<ThongSoEntity> findAllAndJoinFetch();
    ThongSoEntity findByIdAndJoinFetch(int id);
    List<ThongSoEntity> searchByVatLieu(String owner, int id);
    void copySampleDataFromAdmin(int accountId, int parentId, String vatLieuName,
                                 String hangMucName, String noithatName, String phongcachName);
}
