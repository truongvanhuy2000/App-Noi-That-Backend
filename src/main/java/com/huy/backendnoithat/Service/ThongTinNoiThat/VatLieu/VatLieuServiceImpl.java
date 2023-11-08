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
    final HangMucService hangMucService;
    final NoiThatDAO noiThatDAO;
    final HangMucDAO hangMucDAO;
    @Autowired
    public VatLieuServiceImpl(VatLieuDAO vatLieuDAO, ThongSoService thongSoService, AccountService accountService, 
                              JwtTokenUtil jwtTokenUtil, HangMucService hangMucService, NoiThatDAO noiThatDAO, HangMucDAO hangMucDAO) {
        this.vatLieuDAO = vatLieuDAO;
        this.thongSoService = thongSoService;
        this.accountService = accountService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.hangMucService = hangMucService;
        this.noiThatDAO = noiThatDAO;
        this.hangMucDAO = hangMucDAO;
    }
    @Override
    public List<VatLieu> findAll(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return vatLieuDAO.findAll(username).stream().map(item -> new VatLieu(item, true)).toList();
    }
    @Override
    public VatLieu findUsingId(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return new VatLieu(vatLieuDAO.findById(username, id), true);
    }
    @Override
    public VatLieu findUsingName(String token, String name) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return new VatLieu(vatLieuDAO.findUsingName(username, name), true);
    }
    @Override
    public void save(String token, VatLieu vatLieu, int parentId) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        VatLieuEntity vatLieuEntity = new VatLieuEntity(vatLieu);
        vatLieuDAO.save(username, vatLieuEntity, parentId);
    }
    @Override
    public void deleteById(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        vatLieuDAO.deleteById(username, id);
    }
    @Override
    public void update(String token, VatLieu vatLieu) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        vatLieuDAO.update(username, new VatLieuEntity(vatLieu));
    }

    @Override
    public List<VatLieu> joinFetchVatLieu() {
//        return vatLieuDAO.findAllAndJoinFetch().stream().map(item -> new VatLieu(item, true)).toList();
        return null;
    }

    @Override
    public List<VatLieu> searchByHangMuc(String token, int id) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return vatLieuDAO.searchByHangMuc(username, id)
                .stream().map(item -> new VatLieu(item, true)).toList();
    }

    @Override
    public List<VatLieu> searchBy(String token, String phongCachName, String noiThatName, String hangMucName) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return vatLieuDAO.searchBy(username, phongCachName, noiThatName, hangMucName)
                .stream().map(item -> new VatLieu(item, true)).toList();
    }

    @Override
    public void copySampleDataFromAdmin(String token, int parentId) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        HangMucEntity hangMucEntity = hangMucDAO.findById(username, parentId);
        String hangMucName = hangMucEntity.getName();
        String noiThatName = hangMucEntity.getNoiThatEntity().getName();
        String phongcachName = hangMucEntity.getNoiThatEntity().getPhongCachNoiThatEntity().getName();

        copySampleDataFromAdmin(account.getId(), parentId, hangMucName, noiThatName, phongcachName);
    }
    @Override
    public void copySampleDataFromAdmin(int destinationId, int parentId, String hangMucName,
                                        String noiThatName, String phongCachName) {
        vatLieuDAO.copySampleDataFromAdmin(destinationId, parentId, hangMucName, noiThatName, phongCachName);
    }

    @Override
    public void swap(String token, int id1, int id2) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Account account = accountService.findByUsername(username);
        vatLieuDAO.swap(account.getId(), id1, id2);
    }
}
