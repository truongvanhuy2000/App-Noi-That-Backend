package com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.Entity.ThongSoEntity;
import com.huy.backendnoithat.DataModel.ThongSo;

import java.util.List;

public interface ThongSoService {
    List<ThongSo> findAll();
    ThongSo findUsingId(int id);
    ThongSo findUsingName(String name);
    void save(ThongSoEntity thongSoEntity);
    void deleteById(int id);
    void update(ThongSoEntity thongSoEntity);

    List<ThongSo> searchByVatLieu(int id);
}
