package com.huy.backendnoithat.controller.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.HangMuc;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.HangMucService;
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
@Deprecated
@RestController
@Tag(name = "V0HangMucController")
@RequestMapping("/api/hangmuc")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HangMucController {
    private final HangMucService hangMucService;

    @GetMapping("")
    public List<HangMuc> findAll() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return hangMucService.findAll(token);
    }

    @GetMapping("/search")
    public HangMuc findUsingName(@RequestParam(value = "name") String name) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return hangMucService.findUsingName(token, name);
    }

    @GetMapping("/{id}")
    public HangMuc findById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return hangMucService.findUsingId(token, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById( @PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        hangMucService.deleteById(token, id);
    }

    @PutMapping("")
    public void update(@RequestBody HangMuc hangMuc) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        hangMucService.update(token, hangMuc);
    }

    @PostMapping("")
    public void save(@RequestBody HangMuc hangMuc, @RequestParam("parentId") int parentId) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        hangMucService.save(token, hangMuc, parentId);
    }

    @GetMapping("/searchByNoiThat/{id}")
    public List<HangMuc> searchByNoiThat(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return hangMucService.searchByNoiThat(token, id);
    }

    @GetMapping("/searchBy")
    public List<HangMuc> searchBy(
        @RequestParam(value = "phongCachName") String phongCachName,
        @RequestParam(value = "noiThatName") String noiThatName
    ) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return hangMucService.searchBy(token, phongCachName, noiThatName);
    }

    @GetMapping("/copySampleData")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestParam(value = "parentId") int parentId) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        hangMucService.copySampleDataFromAdmin(token, parentId);
        return ResponseEntity.ok("Copied successfully.");
    }

    @GetMapping("/swap")
    public void swap(@RequestParam(value = "id1") int id1, @RequestParam(value = "id2") int id2) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        hangMucService.swap(token, id1, id2);
    }
}
