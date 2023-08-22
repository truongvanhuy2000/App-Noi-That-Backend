package com.huy.backendnoithat.Controller.ThongTinNoiTHat;

import com.huy.backendnoithat.Entity.PhongCachNoiThat;
import com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach.PhongCachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/phongcach")
@RestController
public class PhongCachController {
    PhongCachService phongCachService;
    @Autowired
    public PhongCachController(PhongCachService phongCachService) {
        this.phongCachService = phongCachService;
    }
    @GetMapping("")
    public List<PhongCachNoiThat> findAll() {
        List<PhongCachNoiThat> list = phongCachService.findAll();
        return list;
    }
    @GetMapping("/{id}")
    public PhongCachNoiThat findById(@PathVariable int id) {
        return phongCachService.findById(id);
    }
    @GetMapping("/search")
    public PhongCachNoiThat findUsingName(@RequestParam(value = "name") String name) {
        return phongCachService.findUsingName(name);
    }
    @PostMapping("/add")
    public void save(PhongCachNoiThat phongCachNoiThat) {
        phongCachService.save(phongCachNoiThat);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        phongCachService.deleteById(id);
    }
    @PutMapping("/update")
    public void update(@RequestBody PhongCachNoiThat phongCachNoiThat) {
        phongCachService.update(phongCachNoiThat);
    }
}
