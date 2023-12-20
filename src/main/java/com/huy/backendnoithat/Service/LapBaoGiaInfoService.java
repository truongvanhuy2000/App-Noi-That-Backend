package com.huy.backendnoithat.Service;

import com.huy.backendnoithat.DTO.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.DTO.ThongTinCongTyDTO;

public interface LapBaoGiaInfoService {
    LapBaoGiaInfoDTO getLapBaoGiaInfo(String token);


    void saveNoteArea(String token, String noteArea);
    void saveThongTinCongTy(String token, ThongTinCongTyDTO thongTinCongTyDTO);

}
