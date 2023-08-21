package com.huy.backendnoithat.Service.ThongTinNoiThat.Implementation;

import com.huy.backendnoithat.Entity.VatLieu;
import com.huy.backendnoithat.Service.ThongTinNoiThat.Interface.VatLieuService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VatLieuImpl implements VatLieuService {
    @Override
    public List<VatLieu> findAll() {
        return null;
    }

    @Override
    public VatLieu findUsingId(int id) {
        return null;
    }

    @Override
    public VatLieu findUsingName(String name) {
        return null;
    }

    @Override
    public void save(VatLieu vatLieu) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(VatLieu vatLieu) {

    }
}
