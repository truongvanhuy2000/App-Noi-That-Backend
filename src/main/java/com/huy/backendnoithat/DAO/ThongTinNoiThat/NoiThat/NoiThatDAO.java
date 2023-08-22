package com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.Entity.NoiThat;
import com.huy.backendnoithat.Entity.PhongCachNoiThat;

import java.util.List;

public interface NoiThatDAO {
    List<NoiThat> findAll();
    NoiThat findById(int id);
    NoiThat findUsingName(String name);
    void save(NoiThat noiThat);
    void deleteById(int id);
    void update(NoiThat noiThat);
    List<NoiThat> findAllAndJoinFetch();
    NoiThat findByIdAndJoinFetch(int id);
}
