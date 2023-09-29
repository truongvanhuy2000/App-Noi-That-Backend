package com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.Entity.BangNoiThat.NoiThatEntity;

import java.util.List;

public interface NoiThatDAO {
    List<NoiThatEntity> findAll(String owner);
    NoiThatEntity findById(String owner, int id);
    NoiThatEntity findUsingName(String owner, String name);
    void save(String owner, NoiThatEntity noiThatEntity, int parentId);
    void deleteById(String owner, int id);
    void update(String owner, NoiThatEntity noiThatEntity);
    List<NoiThatEntity> findAllAndJoinFetch();
    NoiThatEntity findByIdAndJoinFetch(int id);

    List<NoiThatEntity> searchByPhongCach(String owner, int id);

    List<NoiThatEntity> searchByParentName(String owner, String phongCachName);
}
