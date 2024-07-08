package com.huy.backendnoithat.service.general;

import com.huy.backendnoithat.model.dto.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.model.dto.ThongTinCongTyDTO;

import java.util.Date;

public interface LapBaoGiaInfoService {
    LapBaoGiaInfoDTO getLapBaoGiaInfo(String token);

    void saveNoteArea(String token, String noteArea);

    void saveThongTinCongTy(String token, ThongTinCongTyDTO thongTinCongTyDTO);

    boolean checkInfoModification(String token, Date date);

    ThongTinCongTyDTO getThongTinCongTy(String token);

    String getNoteArea(String token);
}
