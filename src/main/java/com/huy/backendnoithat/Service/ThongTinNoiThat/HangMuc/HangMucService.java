package com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.Entity.HangMuc;

import java.util.List;

public interface HangMucService {
    // Hang muc
    List<HangMuc> findAll();
    HangMuc findUsingId(int id);
    HangMuc findUsingName(String name);
    void save(HangMuc hangMuc);
    void deleteById(int id);
    void update(HangMuc hangMuc);
}
