package com.huy.backendnoithat.controller.v1.sheet;

import com.huy.backendnoithat.model.PaginationRequest;
import com.huy.backendnoithat.model.PaginationResponse;
import com.huy.backendnoithat.model.dto.BangNoiThat.VatLieu;
import com.huy.backendnoithat.service.v1.sheet.DefaultVatLieuService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api/v1/vat-lieu")
@RestController("V1VatLieuController")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "V1VatLieuController")
public class VatLieuController {
    private final DefaultVatLieuService vatLieuService;

    @GetMapping("")
    public List<VatLieu> findAllVatLieu() {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return vatLieuService.findAll(userID);
    }

    @GetMapping("/{id}")
    public VatLieu findVatLieuById(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return vatLieuService.findById(userID, id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "VatLieu not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteVatLieuById(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        vatLieuService.deleteById(userID, id);
    }

    @PutMapping("/{id}")
    public void updateVatLieu(@PathVariable int id, @RequestBody VatLieu vatLieu) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        vatLieuService.update(userID, id, vatLieu);
    }

    @PostMapping("")
    public void saveVatLieu(@RequestBody VatLieu vatLieu, @RequestParam("parentId") int parentId) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        vatLieuService.save(userID, vatLieu, parentId);
    }

    @GetMapping("/hang-muc/{id}")
    public List<VatLieu> searchByHangMuc(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return vatLieuService.searchByHangMuc(userID, id);
    }

    @GetMapping("/search/hang-muc/{id}")
    public PaginationResponse<List<VatLieu>> searchByHangMucPagination(@PathVariable int id, @ModelAttribute PaginationRequest paginationRequest) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return vatLieuService.searchByHangMuc(userID, id, paginationRequest);
    }

    @GetMapping("/swap")
    public void swapVatLieu(@RequestParam(value = "id1") int id1, @RequestParam(value = "id2") int id2) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        vatLieuService.swap(userID, id1, id2);
    }
}

