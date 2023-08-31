package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat.NoiThatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/noithat")
@RestController
public class NoiThatController {
    NoiThatService noiThatService;
    @Autowired
    public NoiThatController(NoiThatService noiThatService) {
        this.noiThatService = noiThatService;
    }
    @GetMapping("")
    public List<NoiThat> findAll() {
        return noiThatService.findAll();
    }
    @GetMapping("/{id}")
    public NoiThat findById(@PathVariable int id) {
        return noiThatService.findUsingId(id);
    }
    @GetMapping("/search")
    public NoiThat findUsingName(@RequestParam String name) {
        return noiThatService.findUsingName(name);
    }
    @PostMapping("")
    public void save(@RequestBody NoiThat noiThat, @RequestParam("parentId") int parentId) {
        noiThatService.save(noiThat, parentId);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        noiThatService.deleteById(id);
    }
    @PutMapping("")
    public void update(@RequestBody NoiThat noiThat) {
        noiThatService.update(noiThat);
    }
    @GetMapping("/searchByPhongCach/{id}")
    public List<NoiThat> searchByPhongCach(@PathVariable int id) {
        return noiThatService.searchByPhongCach(id);
    }
    // Dont use this API yet
    @GetMapping("/fetch")
    public List<NoiThat> joinFetchNoiThat() {
        return noiThatService.joinFetchNoiThat();
    }
    @GetMapping("/fetch/{id}")
    public NoiThat joinFetchNoiThatUsingId(@PathVariable int id) {
        return noiThatService.joinFetchNoiThatUsingId(id);
    }

}
