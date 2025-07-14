package com.huy.backendnoithat.controller.v1.noithat;

import com.huy.backendnoithat.model.dto.BangNoiThat.HangMuc;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.HangMucService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/hang-muc")
@RestController("V1HangMucController")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "V1HangMucController")
public class HangMucController {
    private final HangMucService hangMucService;

    @GetMapping("")
    public List<HangMuc> findAllHangMuc() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return hangMucService.findAll(token);
    }

    @GetMapping("/{id}")
    public HangMuc findHangMucById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return hangMucService.findUsingId(token, id);
    }

    @DeleteMapping("/{id}")
    public void deleteHangMucById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        hangMucService.deleteById(token, id);
    }

    @PutMapping("")
    public void updateHangMuc(@RequestBody HangMuc hangMuc) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        hangMucService.update(token, hangMuc);
    }

    @PostMapping("")
    public void saveHangMuc(@RequestBody HangMuc hangMuc, @RequestParam("parentId") int parentId) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        hangMucService.save(token, hangMuc, parentId);
    }

    @GetMapping("/find/noi-that/{id}")
    public List<HangMuc> searchHangMucByNoiThat(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return hangMucService.searchByNoiThat(token, id);
    }

    @GetMapping("/swap")
    public void swapHangMuc(@RequestParam(value = "id1") int id1, @RequestParam(value = "id2") int id2) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        hangMucService.swap(token, id1, id2);
    }
}
