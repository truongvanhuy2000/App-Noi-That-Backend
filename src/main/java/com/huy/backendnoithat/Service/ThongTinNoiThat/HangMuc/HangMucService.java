package com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.Entity.HangMucEntity;
import com.huy.backendnoithat.DataModel.HangMuc;

import java.util.List;

public interface HangMucService {
    // Hang muc
    List<HangMuc> findAll();
    HangMuc findUsingId(int id);
    HangMuc findUsingName(String name);
    void save(HangMucEntity hangMucEntity);
    void deleteById(int id);
    void update(HangMucEntity hangMucEntity);

    List<HangMuc> joinFetchHangMuc();

    HangMuc joinFetchHangMucUsingId(int id);

    List<HangMuc> searchByNoiThat(int id);
}
