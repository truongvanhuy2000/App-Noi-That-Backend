package com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.Entity.NoiThatEntity;
import com.huy.backendnoithat.DataModel.NoiThat;

import java.util.List;

public interface NoiThatService {

    // Noi that
    List<NoiThat> findAll();
    NoiThat findUsingId(int id);
    NoiThat findUsingName(String name);
    void save(NoiThatEntity noiThatEntity);
    void deleteById(int id);
    void update(NoiThatEntity noiThatEntity);

    List<NoiThat> joinFetchNoiThat();

    NoiThat joinFetchNoiThatUsingId(int id);

    List<NoiThat> searchByPhongCach(int id);
}
