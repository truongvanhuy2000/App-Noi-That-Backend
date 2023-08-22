package com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.Entity.NoiThat;

import java.util.List;

public interface NoiThatService {

    // Noi that
    List<NoiThat> findAll();
    NoiThat findUsingId(int id);
    NoiThat findUsingName(String name);
    void save(NoiThat noiThat);
    void deleteById(int id);
    void update(NoiThat noiThat);
}
