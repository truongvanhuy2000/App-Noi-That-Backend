package com.huy.backendnoithat.Service.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;

import java.util.List;

public interface BangNoiThatService {
    List<PhongCachNoiThatEntity> fetchAll();
}
