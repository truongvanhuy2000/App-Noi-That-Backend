package com.huy.backendnoithat.controller.v1.sheet;

import com.huy.backendnoithat.model.dto.BangNoiThat.HangMuc;
import com.huy.backendnoithat.service.v1.sheet.DefaultHangMucService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api/v1/hang-muc")
@RestController("V1HangMucController")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "V1HangMucController")
public class HangMucController {
    private final DefaultHangMucService hangMucService;

    @GetMapping("")
    public List<HangMuc> findAllHangMuc() {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return hangMucService.findAll(userID);
    }

    @GetMapping("/{id}")
    public HangMuc findHangMucById(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return hangMucService.findById(userID, id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "HangMuc not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteHangMucById(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        hangMucService.deleteById(userID, id);
    }

    @PutMapping("/{id}")
    public void updateHangMuc(@PathVariable int id, @RequestBody HangMuc hangMuc) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        hangMucService.update(userID, id, hangMuc);
    }

    @PostMapping("")
    public void saveHangMuc(@RequestBody HangMuc hangMuc, @RequestParam("parentId") int parentId) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        hangMucService.save(userID, hangMuc, parentId);
    }

    @GetMapping("/noi-that/{id}")
    public List<HangMuc> searchHangMucByNoiThat(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return hangMucService.searchByNoiThat(userID, id);
    }

    @GetMapping("/swap")
    public void swapHangMuc(@RequestParam(value = "id1") int id1, @RequestParam(value = "id2") int id2) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        hangMucService.swap(userID, id1, id2);
    }
}
