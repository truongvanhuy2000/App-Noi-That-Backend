package com.huy.backendnoithat.controller.v1.sheet;

import com.huy.backendnoithat.model.dto.BangNoiThat.PhongCach;
import com.huy.backendnoithat.service.v1.sheet.DefaultPhongCachService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api/v1/phong-cach")
@RestController("V1PhongCachController")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "V1PhongCachController")
public class PhongCachController {
    private final DefaultPhongCachService defaultPhongCachService;

    @GetMapping("")
    public List<PhongCach> findAllPhongCach() {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return defaultPhongCachService.findAll(userID);
    }

    @GetMapping("/{id}")
    public PhongCach findPhongCachById(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return defaultPhongCachService.findById(userID, id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PhongCach not found"));
    }

    @PostMapping("")
    public void savePhongCach(@RequestBody PhongCach phongCach) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        defaultPhongCachService.save(userID, phongCach);
    }

    @DeleteMapping("/{id}")
    public void deletePhongCachById(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        defaultPhongCachService.deleteById(userID, id);
    }

    @PutMapping("/{id}")
    public void updatePhongCach(@PathVariable int id, @RequestBody PhongCach phongCach) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        defaultPhongCachService.update(userID, id, phongCach);
    }

    @GetMapping("/swap")
    public void swapPhongCach(@RequestParam(value = "id1") int id1, @RequestParam(value = "id2") int id2) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        defaultPhongCachService.swap(userID, id1, id2);
    }
}

