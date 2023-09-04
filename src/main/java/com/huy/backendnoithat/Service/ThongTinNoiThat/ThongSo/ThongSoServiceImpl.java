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
    VatLieuService vatLieuService;
    @Autowired
    public ThongSoServiceImpl(ThongSoDAO thongSoDAO, VatLieuService vatLieuService) {
        this.thongSoDAO = thongSoDAO;
        this.vatLieuService = vatLieuService;
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
    public void save(ThongSo thongSo, int parentId) {
        ThongSoEntity thongSoEntity = new ThongSoEntity(thongSo);
        VatLieuEntity vatLieuEntity = new VatLieuEntity(vatLieuService.findUsingId(parentId));
        thongSoEntity.setVatLieuEntity(vatLieuEntity);
        thongSoDAO.save(thongSoEntity);
    }

    @Override
    public void deleteById(int id) {
        thongSoDAO.deleteById(id);
    }

    @Override
    public void update(ThongSo thongSo) {
        thongSoDAO.update(new ThongSoEntity(thongSo));
    }

    @Override
    public List<ThongSo> searchByVatLieu(int id) {
        return thongSoDAO.searchByVatLieu(id).stream().map(ThongSo::new).toList();
    }
}
