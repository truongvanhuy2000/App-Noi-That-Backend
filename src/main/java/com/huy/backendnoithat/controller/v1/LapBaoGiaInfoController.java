package com.huy.backendnoithat.controller.v1;

import com.huy.backendnoithat.model.dto.LapBaoGiaInfoDTO;
import com.huy.backendnoithat.model.dto.ThongTinCongTyDTO;
import com.huy.backendnoithat.service.general.LapBaoGiaInfoService;
import com.huy.backendnoithat.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController("LapBaoGiaInfoControllerV1")
@RequestMapping("/api/v1/lap-bao-gia-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LapBaoGiaInfoController {
    private final LapBaoGiaInfoService lapBaoGiaInfoService;

    @GetMapping("")
    public LapBaoGiaInfoDTO getLapBaoGiaInfo() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return lapBaoGiaInfoService.getLapBaoGiaInfo(token);
    }

    @PostMapping("/thong-tin-cong-ty")
    public void saveThongTinCongTy(@RequestBody ThongTinCongTyDTO thongTinCongTyDTO) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        lapBaoGiaInfoService.saveThongTinCongTy(token, thongTinCongTyDTO);
    }

    @GetMapping("/thong-tin-cong-ty")
    public ThongTinCongTyDTO getThongTinCongTy() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return lapBaoGiaInfoService.getThongTinCongTy(token);
    }

    @PostMapping("/note-area")
    public void saveNoteArea(@RequestBody String noteArea) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        lapBaoGiaInfoService.saveNoteArea(token, noteArea);
    }

    @GetMapping("/note-area")
    public String getNoteArea() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return lapBaoGiaInfoService.getNoteArea(token);
    }

    @GetMapping("/check-modification")
    public Map<String, Boolean> checkInfoModification(@RequestParam("date") Date date) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        if (lapBaoGiaInfoService.checkInfoModification(token, date)) {
            return Map.of("modified", true);
        } else {
            return Map.of("modified", false);
        }
    }
}
