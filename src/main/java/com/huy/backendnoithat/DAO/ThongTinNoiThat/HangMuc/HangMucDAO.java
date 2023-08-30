package com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.Entity.HangMucEntity;

import java.util.List;

public interface HangMucDAO {
    List<HangMucEntity> findAll();
    HangMucEntity findById(int id);
    HangMucEntity findUsingName(String name);
    void save(HangMucEntity hangMucEntity);
    void deleteById(int id);
    void update(HangMucEntity hangMucEntity);
    List<HangMucEntity> findAllAndJoinFetch();
    HangMucEntity findByIdAndJoinFetch(int id);
    List<HangMucEntity> searchByNoiThat(int id);
}
