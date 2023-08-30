package com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu.VatLieuDAO;
import com.huy.backendnoithat.Entity.VatLieuEntity;
import com.huy.backendnoithat.DataModel.VatLieu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VatLieuServiceImpl implements VatLieuService {
    VatLieuDAO vatLieuDAO;
    @Autowired
    public void setVatLieuDAO(VatLieuDAO vatLieuDAO) {
        this.vatLieuDAO = vatLieuDAO;
    }
    @Override
    public List<VatLieu> findAll() {
        return vatLieuDAO.findAll().stream().map(item -> new VatLieu(item, false)).toList();
    }
    @Override
    public VatLieu findUsingId(int id) {
        return new VatLieu(vatLieuDAO.findById(id), false);
    }
    @Override
    public VatLieu findUsingName(String name) {
        return new VatLieu(vatLieuDAO.findUsingName(name), false);
    }
    @Override
    public void save(VatLieuEntity vatLieuEntity) {
        vatLieuDAO.save(vatLieuEntity);
    }
    @Override
    public void deleteById(int id) {
        vatLieuDAO.deleteById(id);
    }
    @Override
    public void update(VatLieuEntity vatLieuEntity) {
        vatLieuDAO.update(vatLieuEntity);
    }

    @Override
    public List<VatLieu> searchByHangMuc(int id) {
        return vatLieuDAO.searchByHangMuc(id).stream().map(item -> new VatLieu(item, false)).toList();
    }
}
