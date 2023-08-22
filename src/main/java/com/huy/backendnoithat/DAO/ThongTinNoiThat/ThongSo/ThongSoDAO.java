package com.huy.backendnoithat.DAO.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import com.huy.backendnoithat.Entity.ThongSo;

import java.util.List;

public interface ThongSoDAO {
    List<ThongSo> findAll();
    ThongSo findById(int id);
    ThongSo findUsingName(String name);
    void save(ThongSo thongSo);
    void deleteById(int id);
    void update(ThongSo thongSo);
    List<ThongSo> findAllAndJoinFetch();
    ThongSo findByIdAndJoinFetch(int id);
}
