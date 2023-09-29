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
    public List<NoiThat> findAll(@RequestParam(value = "owner") String owner) {
        return noiThatService.findAll(owner);
    }
    @GetMapping("/{id}")
    public NoiThat findById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return noiThatService.findUsingId(owner, id);
    }
    @GetMapping("/search")
    public NoiThat findUsingName(@RequestParam(value = "owner") String owner, @RequestParam(value = "name") String name) {
        return noiThatService.findUsingName(owner, name);
    }
    @PostMapping("")
    public void save(@RequestParam(value = "owner") String owner, @RequestBody NoiThat noiThat, @RequestParam("parentId") int parentId) {
        noiThatService.save(owner, noiThat, parentId);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        noiThatService.deleteById(owner, id);
    }
    @PutMapping("")
    public void update(@RequestParam(value = "owner") String owner, @RequestBody NoiThat noiThat) {
        noiThatService.update(owner, noiThat);
    }
    @GetMapping("/searchByPhongCach/{id}")
    public List<NoiThat> searchByPhongCach(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return noiThatService.searchByPhongCach(owner, id);
    }
    // Dont use this API yet
    @GetMapping("/fetch")
    public List<NoiThat> joinFetchNoiThat(@RequestParam(value = "owner") String owner) {
        return noiThatService.joinFetchNoiThat();
    }
    @GetMapping("/fetch/{id}")
    public NoiThat joinFetchNoiThatUsingId(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return noiThatService.joinFetchNoiThatUsingId(id);
    }
    @GetMapping("/searchByParentName")
    public List<NoiThat> searchByParentName(@RequestParam(value = "owner") String owner,
                                           @RequestParam(value = "phongCachName") String phongCachName) {
        return noiThatService.searchByParentName(owner, phongCachName);
    }
}
