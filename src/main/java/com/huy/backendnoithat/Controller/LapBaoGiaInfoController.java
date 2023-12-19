package com.huy.backendnoithat.Controller;

import com.huy.backendnoithat.DTO.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.DTO.ThongTinCongTyDTO;
import com.huy.backendnoithat.Service.LapBaoGiaInfoService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lapBaoGiaInfo")
public class LapBaoGiaInfoController {
    @Autowired
    LapBaoGiaInfoService lapBaoGiaInfoService;
    @GetMapping("")
    public LapBaoGiaInfoDTO getLapBaoGiaInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return lapBaoGiaInfoService.getLapBaoGiaInfo(token);
    }
    @PostMapping("/thongTinCongTy")
    public LapBaoGiaInfoDTO saveThongTinCongTy(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                              @RequestBody ThongTinCongTyDTO thongTinCongTyDTO) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return lapBaoGiaInfoService.saveThongTinCongTY(token, thongTinCongTyDTO);
    }
}
