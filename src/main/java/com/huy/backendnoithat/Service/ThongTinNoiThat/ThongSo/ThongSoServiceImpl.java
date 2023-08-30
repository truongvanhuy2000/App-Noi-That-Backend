package com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.ThongSo.ThongSoDAO;
import com.huy.backendnoithat.Entity.ThongSoEntity;
import com.huy.backendnoithat.DataModel.ThongSo;
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
    public List<ThongSo> findAll() {
        return thongSoDAO.findAll().stream().map(ThongSo::new).toList();
    }
    @Override
    public ThongSo findUsingId(int id) {
        return new ThongSo(thongSoDAO.findById(id));
    }

    @Override
    public ThongSo findUsingName(String name) {
        return new ThongSo(thongSoDAO.findUsingName(name));
    }

    @Override
    public void save(ThongSoEntity thongSoEntity) {
        thongSoDAO.save(thongSoEntity);
    }

    @Override
    public void deleteById(int id) {
        thongSoDAO.deleteById(id);
    }

    @Override
    public void update(ThongSoEntity thongSoEntity) {
        thongSoDAO.update(thongSoEntity);
    }

    @Override
    public List<ThongSo> searchByVatLieu(int id) {
        return thongSoDAO.searchByVatLieu(id).stream().map(ThongSo::new).toList();
    }
}
