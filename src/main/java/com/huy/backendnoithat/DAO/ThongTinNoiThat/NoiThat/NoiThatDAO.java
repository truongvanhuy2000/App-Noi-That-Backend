package com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;

import java.util.List;

public interface NoiThatDAO {
    List<NoiThatEntity> findAll();
    NoiThatEntity findById(int id);
    NoiThatEntity findUsingName(String name);
    void save(NoiThatEntity noiThatEntity);
    void deleteById(int id);
    void update(NoiThatEntity noiThatEntity);
    List<NoiThatEntity> findAllAndJoinFetch();
    NoiThatEntity findByIdAndJoinFetch(int id);

    List<NoiThatEntity> searchByPhongCach(int id);
}
