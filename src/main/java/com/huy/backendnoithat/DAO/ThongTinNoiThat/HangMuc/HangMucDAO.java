package com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc;

import com.huy.backendnoithat.Entity.HangMuc;
import com.huy.backendnoithat.Entity.NoiThat;

import java.util.List;

public interface HangMucDAO {
    List<HangMuc> findAll();
    HangMuc findById(int id);
    HangMuc findUsingName(String name);
    void save(HangMuc hangMuc);
    void deleteById(int id);
    void update(HangMuc hangMuc);
    List<HangMuc> findAllAndJoinFetch();
    HangMuc findByIdAndJoinFetch(int id);
}
