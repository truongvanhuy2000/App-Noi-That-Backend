package com.huy.backendnoithat.Controller.ThongTinNoiTHat;

import com.huy.backendnoithat.Entity.NoiThat;
import com.huy.backendnoithat.Response.NoiThatResponse;
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
    public List<NoiThatResponse> findAll() {
        return noiThatService.findAll();
    }
    @GetMapping("{id}")
    public NoiThatResponse findById(int id) {
        return noiThatService.findUsingId(id);
    }
    @GetMapping("/search")
    public NoiThatResponse findUsingName(String name) {
        return noiThatService.findUsingName(name);
    }
    @PostMapping("/add")
    public void save(NoiThat noiThat) {
        noiThatService.save(noiThat);
    }
    @DeleteMapping("{id}")
    public void deleteById(int id) {
        noiThatService.deleteById(id);
    }
    @PutMapping("/update")
    public void update(NoiThat noiThat) {
        noiThatService.update(noiThat);
    }

}
