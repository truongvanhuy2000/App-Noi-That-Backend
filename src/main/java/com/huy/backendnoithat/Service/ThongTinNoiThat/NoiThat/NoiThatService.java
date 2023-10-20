package com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;

import java.util.List;

public interface NoiThatService {

    // Noi that
    List<NoiThat> findAll(String owner);
    NoiThat findUsingId(String owner, int id);
    NoiThat findUsingName(String owner, String name);
    void save(String owner, NoiThat noiThat, int parentId);
    void deleteById(String owner, int id);
    void update(String owner, NoiThat noiThat);

    List<NoiThat> joinFetchNoiThat();

    NoiThat joinFetchNoiThatUsingId(int id);

    List<NoiThat> searchByPhongCach(String owner, int id);

    List<NoiThat> searchBy(String owner, String phongCachName);
}
