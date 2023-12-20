package com.huy.backendnoithat.Service;

import com.huy.backendnoithat.DAO.LapBaoGiaInfoDAO;
import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.DTO.ThongTinCongTyDTO;
import com.huy.backendnoithat.Entity.Account.AccountEntity;
import com.huy.backendnoithat.Entity.LapBaoGiaInfoEntity;
import com.huy.backendnoithat.Exception.NotFoundException;
import com.huy.backendnoithat.Service.Account.AccountService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LapBaoGiaInfoServiceImpl implements LapBaoGiaInfoService {
    @NonNull
    LapBaoGiaInfoDAO lapBaoGiaInfoDAO;
    @NonNull
    JwtTokenUtil jwtTokenUtil;
    @NonNull
    AccountService accountService;
    @Value("${logo.path}")
    private static String LOGO_PATH;
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
                        .logo(getLogo(lapBaoGiaInfoEntity.getLogoPath()))
                        .build())
                .note(lapBaoGiaInfoEntity.getNote())
                .build();
    }
    private InputStream getLogo(String fileName) {
        if (fileName == null) {
            return null;
        }
        try {
            return new FileInputStream(Paths.get(LOGO_PATH, fileName).toString());
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    private String saveLogo(InputStream logo) {
        if (logo == null) {
            return null;
        }
        String fileName = "company_logo" + UUID.randomUUID() + System.currentTimeMillis() + ".png";
        try (OutputStream outputStream = new FileOutputStream(Paths.get(LOGO_PATH, fileName).toString())){
            outputStream.write(logo.readAllBytes());
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        lapBaoGiaInfoDAO.save(lapBaoGiaInfoEntity);
    }

    @Override
    public void saveThongTinCongTy(String token, ThongTinCongTyDTO thongTinCongTyDTO) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        LapBaoGiaInfoEntity lapBaoGiaInfoEntity = LapBaoGiaInfoEntity.builder()
                .tenCongTy(thongTinCongTyDTO.getTenCongTy())
                .diaChiVanPhong(thongTinCongTyDTO.getDiaChiVanPhong())
                .diaChiXuong(thongTinCongTyDTO.getDiaChiXuong())
                .soDienThoai(thongTinCongTyDTO.getSoDienThoai())
                .email(thongTinCongTyDTO.getEmail())
                .logoPath(saveLogo(thongTinCongTyDTO.getLogo()))
                .build();
        LapBaoGiaInfoEntity existingInfo = lapBaoGiaInfoDAO.findByUsername(username);
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
}
