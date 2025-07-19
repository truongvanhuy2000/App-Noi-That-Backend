package com.huy.backendnoithat.controller.v1.sheet;

import com.huy.backendnoithat.model.dto.BangNoiThat.ThongSo;
import com.huy.backendnoithat.service.v1.sheet.DefaultThongSoService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api/v1/thong-so")
@RestController("V1ThongSoController")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "V1ThongSoController")
public class ThongSoController {
    private final DefaultThongSoService thongSoService;

    @GetMapping("")
    public List<ThongSo> findAllThongSo() {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return thongSoService.findAll(userID);
    }

    @GetMapping("/{id}")
    public ThongSo findThongSoById(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return thongSoService.findById(userID, id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ThongSo not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteThongSoById(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        thongSoService.deleteById(userID, id);
    }

    @PostMapping("")
    public void saveThongSo(@RequestBody ThongSo thongSo, @RequestParam("parentId") int parentId) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        thongSoService.save(userID, thongSo, parentId);
    }

    @PutMapping("/{id}")
    public void updateThongSo(@PathVariable int id, @RequestBody ThongSo thongSo) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        thongSoService.update(userID, id, thongSo);
    }

    @GetMapping("/vat-lieu/{id}")
    public List<ThongSo> searchByVatLieu(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return thongSoService.searchByVatLieu(userID, id);
    }
}
