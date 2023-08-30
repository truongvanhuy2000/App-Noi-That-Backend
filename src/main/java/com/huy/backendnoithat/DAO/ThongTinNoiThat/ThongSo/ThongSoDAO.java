package com.huy.backendnoithat.DAO.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.Entity.ThongSoEntity;

import java.util.List;

public interface ThongSoDAO {
    List<ThongSoEntity> findAll();
    ThongSoEntity findById(int id);
    ThongSoEntity findUsingName(String name);
    void save(ThongSoEntity thongSoEntity);
    void deleteById(int id);
    void update(ThongSoEntity thongSoEntity);
    List<ThongSoEntity> findAllAndJoinFetch();
    ThongSoEntity findByIdAndJoinFetch(int id);
    List<ThongSoEntity> searchByVatLieu(int id);
}
