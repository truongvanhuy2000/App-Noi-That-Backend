package com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu;

import com.huy.backendnoithat.DAO.ThongTinNoiThat.HangMuc.HangMucDAO;
import com.huy.backendnoithat.DAO.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.DAO.ThongTinNoiThat.VatLieu.VatLieuDAO;
import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;
import com.huy.backendnoithat.Entity.BangNoiThat.HangMucEntity;
import com.huy.backendnoithat.Entity.BangNoiThat.VatLieuEntity;
import com.huy.backendnoithat.Service.Account.AccountService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc.HangMucService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo.ThongSoService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VatLieuServiceImpl implements VatLieuService {
    VatLieuDAO vatLieuDAO;
    ThongSoService thongSoService;
    AccountService accountService;
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    HangMucService hangMucService;
    @Autowired
    NoiThatDAO noiThatDAO;
    @Autowired
    HangMucDAO hangMucDAO;
    @Autowired
    public VatLieuServiceImpl(VatLieuDAO vatLieuDAO, ThongSoService thongSoService, AccountService accountService, JwtTokenUtil jwtTokenUtil) {
        this.vatLieuDAO = vatLieuDAO;
        this.thongSoService = thongSoService;
        this.accountService = accountService;
        this.jwtTokenUtil = jwtTokenUtil;
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
        vatLieuDAO.save(owner, vatLieuEntity, parentId);
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

    @Override
    public List<VatLieu> searchBy(String owner, String phongCachName, String noiThatName, String hangMucName) {
        return vatLieuDAO.searchBy(owner, phongCachName, noiThatName, hangMucName).stream().map(item -> new VatLieu(item, true)).toList();
    }

    @Override
    public void copySampleDataFromAdmin(String token, int parentId) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        HangMucEntity hangMucEntity = hangMucDAO.findById(username, parentId);
        String hangMucName = hangMucEntity.getName();
        String noiThatName = hangMucEntity.getNoiThatEntity().getName();
        String phongcachName = hangMucEntity.getNoiThatEntity().getPhongCachNoiThatEntity().getName();

        vatLieuDAO.copySampleDataFromAdmin(account.getId(), parentId, hangMucName, noiThatName, phongcachName);
    }
}
