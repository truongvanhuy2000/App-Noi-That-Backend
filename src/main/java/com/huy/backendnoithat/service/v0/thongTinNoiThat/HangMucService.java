package com.huy.backendnoithat.service.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.HangMuc;

import java.util.List;

@Deprecated
public interface HangMucService {
    // Hang muc
    List<HangMuc> findAll(String token);

    HangMuc findUsingId(String token, int id);

    HangMuc findUsingName(String token, String name);

    void save(String token, HangMuc hangMuc, int parentId);

    void deleteById(String token, int id);

    void update(String token, HangMuc hangMucEntity);

    List<HangMuc> searchByNoiThat(String token, int id);

    List<HangMuc> searchBy(String token, String phongCachName, String noiThatName);

    void copySampleDataFromAdmin(String token, int parentId);

    void copySampleDataFromAdmin(int destinationId, int parentId, String noiThatName, String phongCachName);

    void swap(String token, int id1, int id2);
}
