package com.huy.backendnoithat.Service.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;
import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;
import com.huy.backendnoithat.DTO.Event.NoiThatUpdate;
import com.huy.backendnoithat.Event.NoiThatUpdateHandler;
import com.huy.backendnoithat.Service.Account.AccountService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc.HangMucService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat.NoiThatService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach.PhongCachService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo.ThongSoService;
import com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu.VatLieuService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class BangNoiThatServiceImpl implements BangNoiThatService{
    private final PhongCachService phongCachService;
    private final NoiThatService noiThatService;
    private final HangMucService hangMucService;
    private final VatLieuService vatLieuService;
    private final ThongSoService thongSoService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountService accountService;
    private final NoiThatUpdateHandler noiThatUpdateHandler;
    @Autowired
    public BangNoiThatServiceImpl(PhongCachService phongCachService, NoiThatService noiThatService,
                                  HangMucService hangMucService, VatLieuService vatLieuService,
                                  ThongSoService thongSoService, JwtTokenUtil jwtTokenUtil,
                                  AccountService accountService, NoiThatUpdateHandler noiThatUpdateHandler) {
        this.phongCachService = phongCachService;
        this.noiThatService = noiThatService;
        this.hangMucService = hangMucService;
        this.vatLieuService = vatLieuService;
        this.thongSoService = thongSoService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountService = accountService;
        this.noiThatUpdateHandler = noiThatUpdateHandler;
    }

    @Override
    public void sampleAll(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
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
        noiThatService.copySampleDataFromAdmin(accountId, phongCach.getId(),phongCach.getName());
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

    @Override
    public Flux<ServerSentEvent<NoiThatUpdate>> getDBModificationEvent(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return Flux.create(sink -> noiThatUpdateHandler.register(username, sink::next)).map(
                noiThatUpdate -> ServerSentEvent.<NoiThatUpdate>builder()
                        .event("noithat-update")
                        .data((NoiThatUpdate) noiThatUpdate)
                        .build()
        );
    }

}

