package com.huy.backendnoithat.controller.v1.sheet;

import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;
import com.huy.backendnoithat.service.v1.sheet.DefaultNoiThatService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api/v1/noi-that")
@RestController("V1NoiThatController")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "V1NoiThatController")
public class NoiThatController {
    private final DefaultNoiThatService defaultNoiThatService;

    @GetMapping("")
    public List<NoiThat> findAllNoiThat() {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return defaultNoiThatService.findAll(userID);
    }

    @GetMapping("/{id}")
    public NoiThat findNoiThatById(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return defaultNoiThatService.findById(userID, id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NoiThat not found"));
    }

    @PostMapping("")
    public void saveNoiThat(@RequestBody NoiThat noiThat, @RequestParam("parentId") int parentId) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        defaultNoiThatService.save(userID, noiThat, parentId);
    }

    @DeleteMapping("/{id}")
    public void deleteNoiThatById(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        defaultNoiThatService.deleteById(userID, id);
    }

    @PutMapping("/{id}")
    public void updateNoiThat(@PathVariable int id, @RequestBody NoiThat noiThat) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        defaultNoiThatService.update(userID, id, noiThat);
    }

    @GetMapping("/phong-cach/{id}")
    public List<NoiThat> searchByPhongCach(@PathVariable int id) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        return defaultNoiThatService.searchByPhongCach(userID, id);
    }

    @GetMapping("/swap")
    public void swapNoiThat(@RequestParam(value = "id1") int id1, @RequestParam(value = "id2") int id2) {
        int userID = SecurityUtils.getUserFromContext(SecurityContextHolder.getContext());
        defaultNoiThatService.swap(userID, id1, id2);
    }
}
