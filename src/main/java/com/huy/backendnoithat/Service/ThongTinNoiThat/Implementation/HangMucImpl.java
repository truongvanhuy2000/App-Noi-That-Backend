package com.huy.backendnoithat.Service.ThongTinNoiThat.Implementation;

import com.huy.backendnoithat.Entity.HangMuc;
import com.huy.backendnoithat.Service.ThongTinNoiThat.Interface.HangMucService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HangMucImpl implements HangMucService {
    @Override
    public List<HangMuc> findAll() {
        return null;
    }

    @Override
    public HangMuc findUsingId(int id) {
        return null;
    }

    @Override
    public HangMuc findUsingName(String name) {
        return null;
    }

    @Override
    public void save(HangMuc hangMuc) {

    }
    @Override
    public void deleteById(int id) {

    }
    @Override
    public void update(HangMuc hangMuc) {

    }
}
