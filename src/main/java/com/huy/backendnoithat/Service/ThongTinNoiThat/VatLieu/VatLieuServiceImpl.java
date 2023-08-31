package com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc.HangMucDAO;
import com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu.VatLieuDAO;
import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;
import com.huy.backendnoithat.Entity.BangNoiThat.HangMucEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.VatLieuEntity;
import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;
import com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc.HangMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VatLieuServiceImpl implements VatLieuService {
    VatLieuDAO vatLieuDAO;
    HangMucService hangMucService;
    @Autowired
    public VatLieuServiceImpl(VatLieuDAO vatLieuDAO, HangMucService hangMucService) {
        this.vatLieuDAO = vatLieuDAO;
        this.hangMucService = hangMucService;
    }
    @Override
    public List<VatLieu> findAll() {
        return vatLieuDAO.findAll().stream().map(item -> new VatLieu(item, true)).toList();
    }
    @Override
    public VatLieu findUsingId(int id) {
        return new VatLieu(vatLieuDAO.findById(id), true);
    }
    @Override
    public VatLieu findUsingName(String name) {
        return new VatLieu(vatLieuDAO.findUsingName(name), true);
    }
    @Override
    public void save(VatLieu vatLieu, int parentId) {
        VatLieuEntity vatLieuEntity = new VatLieuEntity(vatLieu);
        HangMucEntity hangMucEntity = new HangMucEntity(hangMucService.findUsingId(parentId));
        vatLieuEntity.setHangMucEntity(hangMucEntity);
        vatLieuDAO.save(new VatLieuEntity(vatLieu));
    }
    @Override
    public void deleteById(int id) {
        vatLieuDAO.deleteById(id);
    }
    @Override
    public void update(VatLieu vatLieu) {
        vatLieuDAO.update(new VatLieuEntity(vatLieu));
    }

    @Override
    public List<VatLieu> joinFetchVatLieu() {
        return vatLieuDAO.findAllAndJoinFetch().stream().map(item -> new VatLieu(item, true)).toList();
    }

    @Override
    public List<VatLieu> searchByHangMuc(int id) {
        return vatLieuDAO.searchByHangMuc(id).stream().map(item -> new VatLieu(item, true)).toList();
    }
}
