package com.huy.backendnoithat.controller.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.ThongSo;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.ThongSoService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RequestMapping("/api/thongso")
@RestController
@Deprecated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "V0ThongSoController")
public class ThongSoController {
    private final ThongSoService thongSoService;

    @GetMapping("")
    public List<ThongSo> findAll() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return thongSoService.findAll(token);
    }

    @GetMapping("/search")
    public ThongSo findUsingName(@RequestParam String name) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return thongSoService.findUsingName(token, name);
    }

    @GetMapping("/{id}")
    public ThongSo findById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return thongSoService.findUsingId(token, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        thongSoService.deleteById(token, id);
    }

    @PostMapping("")
    public void save(@RequestBody ThongSo thongSo, @RequestParam("parentId") int parentId) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        thongSoService.save(token, thongSo, parentId);
    }

    @PutMapping("")
    public void update(@RequestBody ThongSo thongSo) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        thongSoService.update(token, thongSo);
    }

    @GetMapping("/searchByVatlieu/{id}")
    public List<ThongSo> searchByVatLieu(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return thongSoService.searchByVatLieu(token, id);
    }
}
