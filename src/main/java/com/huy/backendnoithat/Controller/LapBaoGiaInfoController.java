package com.huy.backendnoithat.Controller;

import com.huy.backendnoithat.DTO.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.DTO.ThongTinCongTyDTO;
import com.huy.backendnoithat.Service.LapBaoGiaInfoService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lapBaoGiaInfo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LapBaoGiaInfoController {
    @NonNull
    LapBaoGiaInfoService lapBaoGiaInfoService;
    @GetMapping("")
    public LapBaoGiaInfoDTO getLapBaoGiaInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return lapBaoGiaInfoService.getLapBaoGiaInfo(token);
    }
    @PostMapping("/thongTinCongTy")
    public void saveThongTinCongTy(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                              @RequestBody ThongTinCongTyDTO thongTinCongTyDTO) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        lapBaoGiaInfoService.saveThongTinCongTy(token, thongTinCongTyDTO);
    }
    @PostMapping("/noteArea")
    public void saveNoteArea(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                   @RequestBody String noteArea) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        lapBaoGiaInfoService.saveNoteArea(token, noteArea);
    }
}
