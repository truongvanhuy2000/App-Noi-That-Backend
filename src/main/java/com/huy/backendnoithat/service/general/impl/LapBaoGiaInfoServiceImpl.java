package com.huy.backendnoithat.service.general.impl;

import com.huy.backendnoithat.service.general.LapBaoGiaInfoService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import com.huy.backendnoithat.dao.LapBaoGiaInfoDAO;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.model.dto.ThongTinCongTyDTO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.entity.LapBaoGiaInfoEntity;
import com.huy.backendnoithat.exception.NotFoundException;
import com.huy.backendnoithat.service.account.AccountService;
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
    private final JwtTokenUtil jwtTokenUtil;
    private final AccountService accountService;
    @Value("${logo.path}")
    private String LOGO_PATH;

    @Override
    public LapBaoGiaInfoDTO getLapBaoGiaInfo(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
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

    private byte[] getLogo(String fileName) {
        if (fileName == null) {
            return null;
        }
        try (InputStream file = new FileInputStream(Paths.get(LOGO_PATH, fileName).toString())) {
            return file.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String saveLogo(byte[] logo) {
        if (logo == null) {
            return null;
        }
        String fileName = "company_logo" + UUID.randomUUID() + System.currentTimeMillis() + ".png";
        try (FileOutputStream out = new FileOutputStream(Paths.get(LOGO_PATH, fileName).toAbsolutePath().toString())) {
            out.write(logo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    @Override
    public void saveNoteArea(String token, String noteArea) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
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
        String username = jwtTokenUtil.getUsernameFromToken(token);
        LapBaoGiaInfoEntity existingInfo = lapBaoGiaInfoDAO.findByUsername(username);
        if (existingInfo != null && existingInfo.getLogoPath() != null) {
            deleteExistingLogo(existingInfo.getLogoPath());
        }
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = LapBaoGiaInfoEntity.builder()
                .tenCongTy(thongTinCongTyDTO.getTenCongTy())
                .diaChiVanPhong(thongTinCongTyDTO.getDiaChiVanPhong())
                .diaChiXuong(thongTinCongTyDTO.getDiaChiXuong())
                .soDienThoai(thongTinCongTyDTO.getSoDienThoai())
                .email(thongTinCongTyDTO.getEmail())
                .modifiedDate(thongTinCongTyDTO.getCreatedDate())
                .logoPath(saveLogo(thongTinCongTyDTO.getLogo()))
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

    private void deleteExistingLogo(String fileName) {
        if (fileName == null) {
            return;
        }
        File file = new File(Paths.get(LOGO_PATH, fileName).toString());
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public boolean checkInfoModification(String token, Date date) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByUsername(username);
        if (lapBaoGiaInfoEntity == null) {
            return true;
        }
        return lapBaoGiaInfoEntity.getModifiedDate().compareTo(date) > 0;
    }

    @Override
    public ThongTinCongTyDTO getThongTinCongTy(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByUsername(username);
        if (lapBaoGiaInfoEntity == null) {
            throw new NotFoundException("Không tìm thấy thông tin");
        }
        return ThongTinCongTyDTO.builder()
                .tenCongTy(lapBaoGiaInfoEntity.getTenCongTy())
                .diaChiVanPhong(lapBaoGiaInfoEntity.getDiaChiVanPhong())
                .diaChiXuong(lapBaoGiaInfoEntity.getDiaChiXuong())
                .soDienThoai(lapBaoGiaInfoEntity.getSoDienThoai())
                .email(lapBaoGiaInfoEntity.getEmail())
                .createdDate(lapBaoGiaInfoEntity.getModifiedDate())
                .logo(getLogo(lapBaoGiaInfoEntity.getLogoPath()))
                .build();
    }

    @Override
    public String getNoteArea(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = lapBaoGiaInfoDAO.findByUsername(username);
        if (lapBaoGiaInfoEntity == null) {
            throw new NotFoundException("Không tìm thấy thông tin");
        }
        return lapBaoGiaInfoEntity.getNote();
    }

}
