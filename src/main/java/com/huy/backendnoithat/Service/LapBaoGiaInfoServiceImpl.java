package com.huy.backendnoithat.Service;

import com.huy.backendnoithat.DAO.LapBaoGiaInfoDAO;
import com.huy.backendnoithat.DTO.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.DTO.ThongTinCongTyDTO;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LapBaoGiaInfoServiceImpl implements LapBaoGiaInfoService {
    @Autowired
    LapBaoGiaInfoDAO lapBaoGiaInfoDAO;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Override
    public LapBaoGiaInfoDTO getLapBaoGiaInfo(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return lapBaoGiaInfoDAO.find;
    }

    @Override
    public LapBaoGiaInfoDTO saveLapBaoGiaInfo(String token, LapBaoGiaInfoDTO lapBaoGiaInfoDTO) {
        return null;
    }

    @Override
    public LapBaoGiaInfoDTO saveThongTinCongTY(String token, ThongTinCongTyDTO thongTinCongTyDTO) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return null;
    }
}
