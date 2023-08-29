package com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.Entity.ThongSo;
import com.huy.backendnoithat.Response.ThongSoResponse;

import java.util.List;

public interface ThongSoService {
    List<ThongSoResponse> findAll();
    ThongSoResponse findUsingId(int id);
    ThongSoResponse findUsingName(String name);
    void save(ThongSo thongSo);
    void deleteById(int id);
    void update(ThongSo thongSo);
}
