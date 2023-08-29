package com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.Entity.HangMuc;
import com.huy.backendnoithat.Response.HangMucResponse;

import java.util.List;

public interface HangMucService {
    // Hang muc
    List<HangMucResponse> findAll();
    HangMucResponse findUsingId(int id);
    HangMucResponse findUsingName(String name);
    void save(HangMuc hangMuc);
    void deleteById(int id);
    void update(HangMuc hangMuc);
}
