package com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.ThongSo.ThongSoDAO;
import com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu.VatLieuDAO;
import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;
import com.huy.backendnoithat.Entity.BangNoiThat.ThongSoEntity;
import com.huy.backendnoithat.DTO.BangNoiThat.ThongSo;
import com.huy.backendnoithat.Entity.BangNoiThat.VatLieuEntity;
import com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu.VatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ThongSoServiceImpl implements ThongSoService {
    ThongSoDAO thongSoDAO;

    @Autowired
    public ThongSoServiceImpl(ThongSoDAO thongSoDAO) {
        this.thongSoDAO = thongSoDAO;
    }
    @Override
    public List<ThongSo> findAll(String owner) {
        return thongSoDAO.findAll(owner).stream().map(ThongSo::new).toList();
    }
    @Override
    public ThongSo findUsingId(String owner, int id) {
        return new ThongSo(thongSoDAO.findById(owner, id));
    }

    @Override
    public ThongSo findUsingName(String owner, String name) {
        return new ThongSo(thongSoDAO.findUsingName(owner, name));
    }

    @Override
    public void save(String owner, ThongSo thongSo, int parentId) {
        ThongSoEntity thongSoEntity = new ThongSoEntity(thongSo);
        thongSoDAO.save(owner, thongSoEntity, parentId);
    }

    @Override
    public void deleteById(String owner, int id) {
        thongSoDAO.deleteById(owner, id);
    }

    @Override
    public void update(String owner, ThongSo thongSo) {
        thongSoDAO.update(owner, new ThongSoEntity(thongSo));
    }

    @Override
    public List<ThongSo> searchByVatLieu(String owner, int id) {
        return thongSoDAO.searchByVatLieu(owner, id).stream().map(ThongSo::new).toList();
    }
}
