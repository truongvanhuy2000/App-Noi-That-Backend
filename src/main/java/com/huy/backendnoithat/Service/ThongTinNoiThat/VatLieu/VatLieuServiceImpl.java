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
    public List<VatLieu> findAll(String owner) {
        return vatLieuDAO.findAll(owner).stream().map(item -> new VatLieu(item, true)).toList();
    }
    @Override
    public VatLieu findUsingId(String owner, int id) {
        return new VatLieu(vatLieuDAO.findById(owner, id), true);
    }
    @Override
    public VatLieu findUsingName(String owner, String name) {
        return new VatLieu(vatLieuDAO.findUsingName(owner, name), true);
    }
    @Override
    public void save(String owner, VatLieu vatLieu, int parentId) {
        VatLieuEntity vatLieuEntity = new VatLieuEntity(vatLieu);
        HangMucEntity hangMucEntity = new HangMucEntity(hangMucService.findUsingId(owner, parentId));
        vatLieuEntity.setHangMucEntity(hangMucEntity);
        vatLieuDAO.save(owner, vatLieuEntity);
    }
    @Override
    public void deleteById(String owner, int id) {
        vatLieuDAO.deleteById(owner, id);
    }
    @Override
    public void update(String owner, VatLieu vatLieu) {
        vatLieuDAO.update(owner, new VatLieuEntity(vatLieu));
    }

    @Override
    public List<VatLieu> joinFetchVatLieu() {
//        return vatLieuDAO.findAllAndJoinFetch().stream().map(item -> new VatLieu(item, true)).toList();
        return null;
    }

    @Override
    public List<VatLieu> searchByHangMuc(String owner, int id) {
        return vatLieuDAO.searchByHangMuc(owner, id).stream().map(item -> new VatLieu(item, true)).toList();
    }
}
