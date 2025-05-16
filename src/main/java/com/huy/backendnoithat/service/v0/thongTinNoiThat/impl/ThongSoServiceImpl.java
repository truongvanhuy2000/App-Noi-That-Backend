package com.huy.backendnoithat.service.v0.thongTinNoiThat.impl;

import com.huy.backendnoithat.dao.v0.ThongTinNoiThat.ThongSo.ThongSoDAO;
import com.huy.backendnoithat.dao.v0.ThongTinNoiThat.VatLieu.VatLieuDAO;
import com.huy.backendnoithat.entity.BangNoiThat.ThongSoEntity;
import com.huy.backendnoithat.entity.BangNoiThat.VatLieuEntity;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.BangNoiThat.ThongSo;
import com.huy.backendnoithat.service.v0.account.AccountService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.ThongSoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ThongSoServiceImpl implements ThongSoService {
    private final ThongSoDAO thongSoDAO;
    private final VatLieuDAO vatLieuDAO;
    private final AccountService accountService;
    private final JwtTokenService jwtTokenService;

    @Override
    public List<ThongSo> findAll(String token) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return thongSoDAO.findAll(username).stream().map(ThongSo::new).toList();
    }

    @Override
    public ThongSo findUsingId(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return new ThongSo(thongSoDAO.findById(username, id));
    }

    @Override
    public ThongSo findUsingName(String token, String name) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return new ThongSo(thongSoDAO.findUsingName(username, name));
    }

    @Override
    public void save(String token, ThongSo thongSo, int parentId) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        ThongSoEntity thongSoEntity = new ThongSoEntity(thongSo);
        thongSoDAO.save(username, thongSoEntity, parentId);
    }

    @Override
    public void deleteById(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        thongSoDAO.deleteById(username, id);
    }

    @Override
    public void update(String token, ThongSo thongSo) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        thongSoDAO.update(username, new ThongSoEntity(thongSo));
    }

    @Override
    public List<ThongSo> searchByVatLieu(String token, int id) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        return thongSoDAO.searchByVatLieu(username, id).stream().map(ThongSo::new).toList();
    }

    @Override
    public void copySampleDataFromAdmin(String token, int parentId) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        Account account = accountService.findByUsername(username);
        VatLieuEntity vatLieuEntity = vatLieuDAO.findById(username, parentId);
        String vatLieuName = vatLieuEntity.getName();
        String hangMucName = vatLieuEntity.getHangMucEntity().getName();
        String noiThatName = vatLieuEntity.getHangMucEntity().getNoiThatEntity().getName();
        String phongcachName = vatLieuEntity.getHangMucEntity().getNoiThatEntity().getPhongCachNoiThatEntity().getName();
        copySampleDataFromAdmin(account.getId(), parentId, vatLieuName, hangMucName, noiThatName, phongcachName);
    }

    @Override
    public void copySampleDataFromAdmin(int destinationId, int parentId, String vatLieuName,
                                        String hangMucName, String noiThatName, String phongCachName) {
        thongSoDAO.copySampleDataFromAdmin(destinationId, parentId, vatLieuName, hangMucName, noiThatName, phongCachName);
    }
}
