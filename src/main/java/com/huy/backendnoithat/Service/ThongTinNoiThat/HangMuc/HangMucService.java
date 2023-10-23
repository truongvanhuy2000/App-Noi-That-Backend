package com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;

import java.util.List;

public interface HangMucService {
    // Hang muc
    List<HangMuc> findAll(String owner);
    HangMuc findUsingId(String owner, int id);
    HangMuc findUsingName(String owner, String name);
    void save(String owner, HangMuc hangMuc, int parentId);
    void deleteById(String owner, int id);
    void update(String owner, HangMuc hangMucEntity);

    List<HangMuc> joinFetchHangMuc();

    HangMuc joinFetchHangMucUsingId(int id);

    List<HangMuc> searchByNoiThat(String owner, int id);

    List<HangMuc> searchBy(String owner, String phongCachName, String noiThatName);

    void copySampleDataFromAdmin(String token, int parentId);
}
