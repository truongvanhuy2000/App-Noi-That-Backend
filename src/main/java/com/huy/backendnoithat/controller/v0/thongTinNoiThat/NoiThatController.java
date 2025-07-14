package com.huy.backendnoithat.controller.v0.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.NoiThatService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@Deprecated
@RestController
@Tag(name = "V0NoiThatController")
@RequestMapping("/api/noithat")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NoiThatController {
    private final NoiThatService noiThatService;

    @GetMapping("")
    public List<NoiThat> findAll() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return noiThatService.findAll(token);
    }

    @GetMapping("/{id}")
    public NoiThat findById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return noiThatService.findUsingId(token, id);
    }

    @GetMapping("/search")
    public NoiThat findUsingName(@RequestParam(value = "name") String name) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return noiThatService.findUsingName(token, name);
    }

    @PostMapping("")
    public void save(@RequestBody NoiThat noiThat, @RequestParam("parentId") int parentId) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        noiThatService.save(token, noiThat, parentId);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        noiThatService.deleteById(token, id);
    }

    @PutMapping("")
    public void update(@RequestBody NoiThat noiThat) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        noiThatService.update(token, noiThat);
    }

    @GetMapping("/searchByPhongCach/{id}")
    public List<NoiThat> searchByPhongCach(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return noiThatService.searchByPhongCach(token, id);
    }

    @GetMapping("/searchBy")
    public List<NoiThat> searchBy(@RequestParam(value = "phongCachName") String phongCachName) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return noiThatService.searchBy(token, phongCachName);
    }

    @GetMapping("/copySampleData")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestParam(value = "parentId") int parentId) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        noiThatService.copySampleDataFromAdmin(token, parentId);
        return ResponseEntity.ok("Copied successfully.");
    }

    @GetMapping("/swap")
    public void swap(@RequestParam(value = "id1") int id1, @RequestParam(value = "id2") int id2) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        noiThatService.swap(token, id1, id2);
    }
}
