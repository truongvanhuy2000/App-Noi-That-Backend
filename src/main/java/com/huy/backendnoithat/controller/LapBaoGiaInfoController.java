package com.huy.backendnoithat.controller;

import com.huy.backendnoithat.model.dto.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.model.dto.ThongTinCongTyDTO;
import com.huy.backendnoithat.service.general.LapBaoGiaInfoService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/lapBaoGiaInfo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LapBaoGiaInfoController {
    private final LapBaoGiaInfoService lapBaoGiaInfoService;

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

    @GetMapping("/thongTinCongTy")
    public ThongTinCongTyDTO getThongTinCongTy(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return lapBaoGiaInfoService.getThongTinCongTy(token);
    }

    @PostMapping("/noteArea")
    public void saveNoteArea(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                             @RequestBody String noteArea) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        lapBaoGiaInfoService.saveNoteArea(token, noteArea);
    }

    @GetMapping("/noteArea")
    public String getNoteArea(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return lapBaoGiaInfoService.getNoteArea(token);
    }

    @GetMapping("/checkModification")
    public Map<String, Boolean> checkInfoModification(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                      @RequestParam("date") Date date) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        if (lapBaoGiaInfoService.checkInfoModification(token, date)) {
            return Map.of("modified", true);
        } else {
            return Map.of("modified", false);
        }
    }
}
