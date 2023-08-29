package com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.Entity.NoiThat;
import com.huy.backendnoithat.Response.NoiThatResponse;

import java.util.List;

public interface NoiThatService {

    // Noi that
    List<NoiThatResponse> findAll();
    NoiThatResponse findUsingId(int id);
    NoiThatResponse findUsingName(String name);
    void save(NoiThat noiThat);
    void deleteById(int id);
    void update(NoiThat noiThat);
}
