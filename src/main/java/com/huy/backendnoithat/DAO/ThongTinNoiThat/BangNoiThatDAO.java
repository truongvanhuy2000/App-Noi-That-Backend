package com.huy.backendnoithat.DAO.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;

import java.util.List;

public interface BangNoiThatDAO {
    List<PhongCachNoiThatEntity> fetchAll();
}
