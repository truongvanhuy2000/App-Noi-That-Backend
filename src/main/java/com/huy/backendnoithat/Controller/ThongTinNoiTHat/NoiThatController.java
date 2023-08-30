package com.huy.backendnoithat.Controller.ThongTinNoiTHat;

import com.huy.backendnoithat.Entity.NoiThatEntity;
import com.huy.backendnoithat.DataModel.NoiThat;
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
    @GetMapping("{id}")
    public NoiThat findById(int id) {
        return noiThatService.findUsingId(id);
    }
    @GetMapping("/search")
    public NoiThat findUsingName(String name) {
        return noiThatService.findUsingName(name);
    }
    @PostMapping("/add")
    public void save(NoiThatEntity noiThatEntity) {
        noiThatService.save(noiThatEntity);
    }
    @DeleteMapping("{id}")
    public void deleteById(int id) {
        noiThatService.deleteById(id);
    }
    @PutMapping("/update")
    public void update(NoiThatEntity noiThatEntity) {
        noiThatService.update(noiThatEntity);
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
