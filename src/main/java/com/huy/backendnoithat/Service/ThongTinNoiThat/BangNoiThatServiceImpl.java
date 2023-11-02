package com.huy.backendnoithat.Service.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;
import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;
import com.huy.backendnoithat.Service.Account.AccountService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc.HangMucService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat.NoiThatService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach.PhongCachService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo.ThongSoService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu.VatLieuService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BangNoiThatServiceImpl implements BangNoiThatService{
    private PhongCachService phongCachService;
    private NoiThatService noiThatService;
    private HangMucService hangMucService;
    private VatLieuService vatLieuService;
    private ThongSoService thongSoService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountService accountService;
    @Autowired
    public BangNoiThatServiceImpl(PhongCachService phongCachService, NoiThatService noiThatService,
                                  HangMucService hangMucService, VatLieuService vatLieuService,
                                  ThongSoService thongSoService, JwtTokenUtil jwtTokenUtil,
                                  AccountService accountService) {
        this.phongCachService = phongCachService;
        this.noiThatService = noiThatService;
        this.hangMucService = hangMucService;
        this.vatLieuService = vatLieuService;
        this.thongSoService = thongSoService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountService = accountService;
    }

    @Override
    public void sampleAll(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        int accountId = accountService.findByUsername(username).getId();
        samplePhongCach(username, accountId);
    }
    private void samplePhongCach(String username, int accountId) {
        phongCachService.copySampleDataFromAdmin(accountId);
        List<PhongCach> phongCachList = phongCachService.findAll(username);
        for (PhongCach phongCach : phongCachList) {
            sampleNoiThat(username, accountId, phongCach);
        }
    }
    private void sampleNoiThat(String username, int accountId, PhongCach phongCach) {
        noiThatService.copySampleDataFromAdmin(accountId, phongCach.getId(),phongCach.getName());
        List<NoiThat> noiThatList = noiThatService.searchByPhongCach(username, phongCach.getId());
        for (NoiThat noiThat : noiThatList) {
            sampleHangMuc(username, accountId, noiThat, phongCach);
        }
    }
    private void sampleHangMuc(String username, int accountId, NoiThat noiThat, PhongCach phongCach) {
        hangMucService.copySampleDataFromAdmin(accountId, noiThat.getId(), noiThat.getName(), phongCach.getName());
        List<HangMuc> hangMucList = hangMucService.searchByNoiThat(username, noiThat.getId());
        for (HangMuc hangMuc : hangMucList) {
            sampleVatLieu(username, accountId, hangMuc, noiThat, phongCach);
        }
    }
    private void sampleVatLieu(String username, int accountId, HangMuc hangMuc, NoiThat noiThat, PhongCach phongCach) {
        vatLieuService.copySampleDataFromAdmin(accountId, hangMuc.getId(), hangMuc.getName(),
                noiThat.getName(), phongCach.getName());
        List<VatLieu> vatLieuList = vatLieuService.searchByHangMuc(username, hangMuc.getId());
        for (VatLieu vatLieu : vatLieuList) {
            sampleThongSo(username, accountId, vatLieu, hangMuc, noiThat, phongCach);
        }
    }
    private void sampleThongSo(String username, int accountId, VatLieu vatLieu,
                               HangMuc hangMuc, NoiThat noiThat, PhongCach phongCach) {
        thongSoService.copySampleDataFromAdmin(accountId, vatLieu.getId(), vatLieu.getName(),
                hangMuc.getName(), noiThat.getName(), phongCach.getName());
    }
}
