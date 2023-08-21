package com.huy.backendnoithat.Service.ThongTinNoiThat.Implementation;

import com.huy.backendnoithat.Entity.ThongSo;
import com.huy.backendnoithat.Service.ThongTinNoiThat.Interface.ThongSoService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThongSoImpl implements ThongSoService {
    @Override
    public List<ThongSo> findAll() {
        return null;
    }

    @Override
    public ThongSo findUsingId(int id) {
        return null;
    }

    @Override
    public ThongSo findUsingName(String name) {
        return null;
    }

    @Override
    public void save(ThongSo thongSo) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(ThongSo thongSo) {

    }
}
