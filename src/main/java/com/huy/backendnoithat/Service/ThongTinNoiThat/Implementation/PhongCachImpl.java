package com.huy.backendnoithat.Service.ThongTinNoiThat.Implementation;

import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import com.huy.backendnoithat.Service.ThongTinNoiThat.Interface.PhongCachService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PhongCachImpl implements PhongCachService {

    @Override
    public List<PhongCachNoiThat> findAll() {
        return null;
    }

    @Override
    public PhongCachNoiThat findById(int id) {
        return null;
    }

    @Override
    public PhongCachNoiThat findUsingName(String name) {
        return null;
    }

    @Override
    public void save(PhongCachNoiThat phongCachNoiThat) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(PhongCachNoiThat phongCachNoiThat) {

    }
}
