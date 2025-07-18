package com.huy.backendnoithat.service.v0.thongTinNoiThat.impl;

import com.huy.backendnoithat.model.dto.BangNoiThat.HangMuc;
import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;
import com.huy.backendnoithat.model.dto.BangNoiThat.PhongCach;
import com.huy.backendnoithat.model.dto.BangNoiThat.VatLieu;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.service.v0.account.AccountService;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BangNoiThatServiceImpl implements BangNoiThatService {
    private final PhongCachService phongCachService;
    private final NoiThatService noiThatService;
    private final HangMucService hangMucService;
    private final VatLieuService vatLieuService;
    private final ThongSoService thongSoService;
    private final JwtTokenService jwtTokenService;
    private final AccountService accountService;

    @Override
    public void sampleAll(String token) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        int accountId = accountService.findByUsername(username).getId();
        samplePhongCach(token, accountId);
    }

    private void samplePhongCach(String token, int accountId) {
        phongCachService.copySampleDataFromAdmin(accountId);
        List<PhongCach> phongCachList = phongCachService.findAll(token);
        for (PhongCach phongCach : phongCachList) {
            sampleNoiThat(token, accountId, phongCach);
        }
    }

    private void sampleNoiThat(String token, int accountId, PhongCach phongCach) {
        noiThatService.copySampleDataFromAdmin(accountId, phongCach.getId(), phongCach.getName());
        List<NoiThat> noiThatList = noiThatService.searchByPhongCach(token, phongCach.getId());
        for (NoiThat noiThat : noiThatList) {
            sampleHangMuc(token, accountId, noiThat, phongCach);
        }
    }

    private void sampleHangMuc(String token, int accountId, NoiThat noiThat, PhongCach phongCach) {
        hangMucService.copySampleDataFromAdmin(accountId, noiThat.getId(), noiThat.getName(), phongCach.getName());
        List<HangMuc> hangMucList = hangMucService.searchByNoiThat(token, noiThat.getId());
        for (HangMuc hangMuc : hangMucList) {
            sampleVatLieu(token, accountId, hangMuc, noiThat, phongCach);
        }
    }

    private void sampleVatLieu(String token, int accountId, HangMuc hangMuc, NoiThat noiThat, PhongCach phongCach) {
        vatLieuService.copySampleDataFromAdmin(accountId, hangMuc.getId(), hangMuc.getName(),
                noiThat.getName(), phongCach.getName());
        List<VatLieu> vatLieuList = vatLieuService.searchByHangMuc(token, hangMuc.getId());
        for (VatLieu vatLieu : vatLieuList) {
            sampleThongSo(token, accountId, vatLieu, hangMuc, noiThat, phongCach);
        }
    }

    private void sampleThongSo(String token, int accountId, VatLieu vatLieu,
                               HangMuc hangMuc, NoiThat noiThat, PhongCach phongCach) {
        thongSoService.copySampleDataFromAdmin(accountId, vatLieu.getId(), vatLieu.getName(),
                hangMuc.getName(), noiThat.getName(), phongCach.getName());
    }

}

