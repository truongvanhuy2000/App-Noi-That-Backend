package com.huy.backendnoithat.controller.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.VatLieu;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.VatLieuService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated
@RestController
@RequestMapping("/api/vatlieu")
@Tag(name = "V0VatLieuController")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class VatLieuController {
    private final VatLieuService vatLieuService;

    @GetMapping("")
    public List<VatLieu> findAll() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return vatLieuService.findAll(token);
    }

    @GetMapping("/search")
    public VatLieu findUsingName(@RequestParam(value = "name") String name) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return vatLieuService.findUsingName(token, name);
    }

    @GetMapping("/{id}")
    public VatLieu findById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return vatLieuService.findUsingId(token, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        vatLieuService.deleteById(token, id);
    }

    @PutMapping("")
    public void update(@RequestBody VatLieu vatLieu) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        vatLieuService.update(token, vatLieu);
    }

    @PostMapping("")
    public void save(@RequestBody VatLieu vatLieu, @RequestParam("parentId") int parentId) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        vatLieuService.save(token, vatLieu, parentId);
    }

    @GetMapping("/searchByHangMuc/{id}")
    public List<VatLieu> searchByHangMuc(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return vatLieuService.searchByHangMuc(token, id);
    }

    @Deprecated
    @GetMapping("/searchBy")
    public List<VatLieu> searchBy(
        @RequestParam(value = "phongCachName") String phongCachName,
        @RequestParam(value = "noiThatName") String noiThatName,
        @RequestParam(value = "hangMucName") String hangMucName
    ) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return vatLieuService.searchBy(token, phongCachName, noiThatName, hangMucName);
    }

    @GetMapping("/swap")
    public void swap(
        @RequestParam(value = "id1") int id1,
        @RequestParam(value = "id2") int id2
    ) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        vatLieuService.swap(token, id1, id2);
    }
}
