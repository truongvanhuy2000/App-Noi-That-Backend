package com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;

import java.util.List;

public interface NoiThatService {

    // Noi that
    List<NoiThat> findAll();
    NoiThat findUsingId(int id);
    NoiThat findUsingName(String name);
    void save(NoiThat noiThat, int parentId);
    void deleteById(int id);
    void update(NoiThat noiThat);

    List<NoiThat> joinFetchNoiThat();

    NoiThat joinFetchNoiThatUsingId(int id);

    List<NoiThat> searchByPhongCach(int id);
}
