package com.huy.backendnoithat.service.general.implementation;

import com.huy.backendnoithat.dao.LapBaoGiaInfoDAO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.entity.LapBaoGiaInfoEntity;
import com.huy.backendnoithat.exception.NotFoundException;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.model.dto.ThongTinCongTyDTO;
import com.huy.backendnoithat.service.v0.account.AccountService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.service.general.LapBaoGiaInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LapBaoGiaInfoServiceImpl implements LapBaoGiaInfoService {
    private final LapBaoGiaInfoDAO lapBaoGiaInfoDAO;
    private final JwtTokenService jwtTokenService;
    private final AccountService accountService;
    @Value("${logo.path}")
    private String LOGO_PATH;

    @Override
    public LapBaoGiaInfoDTO getLapBaoGiaInfo(String token) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByUsername(username);
        if (lapBaoGiaInfoEntity == null) {
            throw new NotFoundException("Không tìm thấy thông tin");
        }
        return LapBaoGiaInfoDTO.builder()
                .thongTinCongTy(ThongTinCongTyDTO.builder()
                        .tenCongTy(lapBaoGiaInfoEntity.getTenCongTy())
                        .diaChiVanPhong(lapBaoGiaInfoEntity.getDiaChiVanPhong())
                        .diaChiXuong(lapBaoGiaInfoEntity.getDiaChiXuong())
                        .soDienThoai(lapBaoGiaInfoEntity.getSoDienThoai())
                        .email(lapBaoGiaInfoEntity.getEmail())
                        .createdDate(lapBaoGiaInfoEntity.getModifiedDate())
                        .build())
                .note(lapBaoGiaInfoEntity.getNote())
                .build();
    }

    @Override
    public void saveNoteArea(String token, String noteArea) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByUsername(username);
        if (lapBaoGiaInfoEntity == null) {
            Account account = accountService.findByUsername(username);
            lapBaoGiaInfoEntity = LapBaoGiaInfoEntity.builder()
                    .note(noteArea)
                    .account(AccountEntity.builder().id(account.getId()).build())
                    .build();
        } else {
            lapBaoGiaInfoEntity.setNote(noteArea);
        }
        lapBaoGiaInfoEntity.setModifiedDate(new Date());
        lapBaoGiaInfoDAO.save(lapBaoGiaInfoEntity);
    }

    @Override
    public void saveThongTinCongTy(String token, ThongTinCongTyDTO thongTinCongTyDTO) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        LapBaoGiaInfoEntity existingInfo = lapBaoGiaInfoDAO.findByUsername(username);
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = LapBaoGiaInfoEntity.builder()
                .tenCongTy(thongTinCongTyDTO.getTenCongTy())
                .diaChiVanPhong(thongTinCongTyDTO.getDiaChiVanPhong())
                .diaChiXuong(thongTinCongTyDTO.getDiaChiXuong())
                .soDienThoai(thongTinCongTyDTO.getSoDienThoai())
                .email(thongTinCongTyDTO.getEmail())
                .modifiedDate(thongTinCongTyDTO.getCreatedDate())
                .build();
        if (existingInfo == null) {
            Account account = accountService.findByUsername(username);
            lapBaoGiaInfoEntity.setAccount(AccountEntity.builder().id(account.getId()).build());
        } else {
            lapBaoGiaInfoEntity.setId(existingInfo.getId());
            lapBaoGiaInfoEntity.setAccount(existingInfo.getAccount());
            lapBaoGiaInfoEntity.setNote(existingInfo.getNote());
        }
        lapBaoGiaInfoDAO.save(lapBaoGiaInfoEntity);
    }

    @Override
    public boolean checkInfoModification(String token, Date date) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByUsername(username);
        if (lapBaoGiaInfoEntity == null) {
            return true;
        }
        return lapBaoGiaInfoEntity.getModifiedDate().compareTo(date) > 0;
    }

    @Override
    public ThongTinCongTyDTO getThongTinCongTy(String token) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByUsername(username);
        if (lapBaoGiaInfoEntity == null) {
            return null;
        }
        return ThongTinCongTyDTO.builder()
                .tenCongTy(lapBaoGiaInfoEntity.getTenCongTy())
                .diaChiVanPhong(lapBaoGiaInfoEntity.getDiaChiVanPhong())
                .diaChiXuong(lapBaoGiaInfoEntity.getDiaChiXuong())
                .soDienThoai(lapBaoGiaInfoEntity.getSoDienThoai())
                .email(lapBaoGiaInfoEntity.getEmail())
                .createdDate(lapBaoGiaInfoEntity.getModifiedDate())
                .build();
    }

    @Override
    public String getNoteArea(String token) {
        String username = jwtTokenService.getUsernameFromToken(token).orElseThrow();
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByUsername(username);
        if (lapBaoGiaInfoEntity == null) {
            return null;
        }
        return lapBaoGiaInfoEntity.getNote();
    }

}
