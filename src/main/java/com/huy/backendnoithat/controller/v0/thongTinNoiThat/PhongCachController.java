package com.huy.backendnoithat.controller.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.PhongCach;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.PhongCachService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RequestMapping("/api/phongcach")
@RestController
@Deprecated
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "V0PhongCachController")
public class PhongCachController {
    private final PhongCachService phongCachService;

    @GetMapping("")
    public List<PhongCach> findAll() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return phongCachService.findAll(token);
    }

    @GetMapping("/{id}")
    public PhongCach findById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return phongCachService.findById(token, id);
    }

    @GetMapping("/search")
    public PhongCach findUsingName(@RequestParam(value = "name") String name) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return phongCachService.findUsingName(token, name);
    }

    @PostMapping("")
    public void save(@RequestBody PhongCach phongCach) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        phongCachService.save(token, phongCach);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        phongCachService.deleteById(token, id);
    }

    @PutMapping("")
    public void update(@RequestBody PhongCach phongCach) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        phongCachService.update(token, phongCach);
    }

    @GetMapping("/swap")
    public void swap(@RequestParam(value = "id1") int id1, @RequestParam(value = "id2") int id2) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        phongCachService.swap(token, id1, id2);
    }
}
