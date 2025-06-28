package com.huy.backendnoithat.service.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;

import java.util.List;

@Deprecated
public interface NoiThatService {

    // Noi that
    List<NoiThat> findAll(String token);

    NoiThat findUsingId(String token, int id);

    NoiThat findUsingName(String token, String name);

    void save(String token, NoiThat noiThat, int parentId);

    void deleteById(String token, int id);

    void update(String token, NoiThat noiThat);

    List<NoiThat> searchByPhongCach(String token, int id);

    List<NoiThat> searchBy(String token, String phongCachName);

    void copySampleDataFromAdmin(String token, int parentId);

    void copySampleDataFromAdmin(int accountId, int parentId, String parentName);

    void swap(String token, int id1, int id2);
}
