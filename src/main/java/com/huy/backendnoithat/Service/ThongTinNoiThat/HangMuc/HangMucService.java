package com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;

import java.util.List;

public interface HangMucService {
    // Hang muc
    List<HangMuc> findAll();
    HangMuc findUsingId(int id);
    HangMuc findUsingName(String name);
    void save(HangMuc hangMuc, int parentId);
    void deleteById(int id);
    void update(HangMuc hangMucEntity);

    List<HangMuc> joinFetchHangMuc();

    HangMuc joinFetchHangMucUsingId(int id);

    List<HangMuc> searchByNoiThat(int id);
}
