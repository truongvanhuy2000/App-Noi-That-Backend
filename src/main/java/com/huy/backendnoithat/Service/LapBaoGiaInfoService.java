package com.huy.backendnoithat.Service;

import com.huy.backendnoithat.DTO.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.DTO.ThongTinCongTyDTO;

public interface LapBaoGiaInfoService {
    LapBaoGiaInfoDTO getLapBaoGiaInfo(String token);

    LapBaoGiaInfoDTO saveLapBaoGiaInfo(String token, LapBaoGiaInfoDTO lapBaoGiaInfoDTO);

    LapBaoGiaInfoDTO saveThongTinCongTY(String token, ThongTinCongTyDTO thongTinCongTyDTO);
}
