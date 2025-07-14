package com.huy.backendnoithat.controller.v1.noithat;

import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;
import com.huy.backendnoithat.service.v0.thongTinNoiThat.NoiThatService;
import com.huy.backendnoithat.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/noi-that")
@RestController("V1NoiThatController")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "V1NoiThatController")
public class NoiThatController {
    private final NoiThatService noiThatService;

    @GetMapping("")
    public List<NoiThat> findAllNoiThat() {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return noiThatService.findAll(token);
    }

    @GetMapping("/{id}")
    public NoiThat findNoiThatById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return noiThatService.findUsingId(token, id);
    }


    @PostMapping("")
    public void saveNoiThat(@RequestBody NoiThat noiThat, @RequestParam("parentId") int parentId) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        noiThatService.save(token, noiThat, parentId);
    }

    @DeleteMapping("/{id}")
    public void deleteNoiThatById(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        noiThatService.deleteById(token, id);
    }

    @PutMapping("")
    public void updateNoiThat(@RequestBody NoiThat noiThat) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        noiThatService.update(token, noiThat);
    }

    @GetMapping("/find/phong-cach/{id}")
    public List<NoiThat> searchByPhongCach(@PathVariable int id) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        return noiThatService.searchByPhongCach(token, id);
    }

    @GetMapping("/swap")
    public void swapNoiThat(@RequestParam(value = "id1") int id1, @RequestParam(value = "id2") int id2) {
        String token = SecurityUtils.getTokenFromContext(SecurityContextHolder.getContext());
        noiThatService.swap(token, id1, id2);
    }
}
