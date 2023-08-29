package com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.ThongSo.ThongSoDAO;
import com.huy.backendnoithat.Entity.ThongSo;
import com.huy.backendnoithat.Response.ThongSoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThongSoServiceImpl implements ThongSoService {
    ThongSoDAO thongSoDAO;
    @Autowired
    public void setThongSoDAO(ThongSoDAO thongSoDAO) {
        this.thongSoDAO = thongSoDAO;
    }
    @Override
    public List<ThongSoResponse> findAll() {
        return thongSoDAO.findAll().stream().map(ThongSoResponse::new).toList();
    }
    @Override
    public ThongSoResponse findUsingId(int id) {
        return new ThongSoResponse(thongSoDAO.findById(id));
    }

    @Override
    public ThongSoResponse findUsingName(String name) {
        return new ThongSoResponse(thongSoDAO.findUsingName(name));
    }

    @Override
    public void save(ThongSo thongSo) {
        thongSoDAO.save(thongSo);
    }

    @Override
    public void deleteById(int id) {
        thongSoDAO.deleteById(id);
    }

    @Override
    public void update(ThongSo thongSo) {
        thongSoDAO.update(thongSo);
    }
}
