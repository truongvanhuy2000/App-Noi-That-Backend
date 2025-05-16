package com.huy.backendnoithat.service.v0.thongTinNoiThat.impl;

import com.huy.backendnoithat.dao.v0.ThongTinNoiThat.HangMuc.HangMucDAO;
import com.huy.backendnoithat.dao.v0.ThongTinNoiThat.NoiThat.NoiThatDAO;
import com.huy.backendnoithat.dao.v0.ThongTinNoiThat.VatLieu.VatLieuDAO;
import com.huy.backendnoithat.entity.BangNoiThat.HangMucEntity;
import com.huy.backendnoithat.entity.BangNoiThat.VatLieuEntity;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.BangNoiThat.VatLieu;
import com.huy.backendnoithat.service.v0.account.AccountService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.HangMucService;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.ThongSoService;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.VatLieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class VatLieuServiceImpl implements VatLieuService {
    private final VatLieuDAO vatLieuDAO;
    private final ThongSoService thongSoService;
    private final AccountService accountService;
    private final JwtTokenService jwtTokenService;
    private final HangMucService hangMucService;
    private final NoiThatDAO noiThatDAO;
    private final HangMucDAO hangMucDAO;

    @Override
    public List<VatLieu> findAll(String token) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return vatLieuDAO.findAll(username).stream().map(item -> new VatLieu(item, true)).toList();
    }

    @Override
    public VatLieu findUsingId(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return new VatLieu(vatLieuDAO.findById(username, id), true);
    }

    @Override
    public VatLieu findUsingName(String token, String name) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return new VatLieu(vatLieuDAO.findUsingName(username, name), true);
    }

    @Override
    public void save(String token, VatLieu vatLieu, int parentId) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        VatLieuEntity vatLieuEntity = new VatLieuEntity(vatLieu);
        vatLieuDAO.save(username, vatLieuEntity, parentId);
    }

    @Override
    public void deleteById(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        vatLieuDAO.deleteById(username, id);
    }

    @Override
    public void update(String token, VatLieu vatLieu) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        vatLieuDAO.update(username, new VatLieuEntity(vatLieu));
    }

    @Override
    public List<VatLieu> joinFetchVatLieu() {
//        return vatLieuDAO.findAllAndJoinFetch().stream().map(item -> new VatLieu(item, true)).toList();
        return null;
    }

    @Override
    public List<VatLieu> searchByHangMuc(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return vatLieuDAO.searchByHangMuc(username, id)
                .stream().map(item -> new VatLieu(item, true)).toList();
    }

    @Override
    public List<VatLieu> searchBy(String token, String phongCachName, String noiThatName, String hangMucName) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return vatLieuDAO.searchBy(username, phongCachName, noiThatName, hangMucName)
                .stream().map(item -> new VatLieu(item, true)).toList();
    }

    @Override
    public void copySampleDataFromAdmin(String token, int parentId) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
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
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        Account account = accountService.findByUsername(username);
        vatLieuDAO.swap(account.getId(), id1, id2);
    }
}
