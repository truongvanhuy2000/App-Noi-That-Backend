package com.huy.backendnoithat.Controller.ThongTinNoiTHat;

import com.huy.backendnoithat.Entity.PhongCachNoiThatEntity;
import com.huy.backendnoithat.DataModel.PhongCach;
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
    public List<PhongCach> findAll() {
        return phongCachService.findAll();
    }
    @GetMapping("/{id}")
    public PhongCach findById(@PathVariable int id) {
        return phongCachService.findById(id);
    }
    @GetMapping("/search")
    public PhongCach findUsingName(@RequestParam(value = "name") String name) {
        return phongCachService.findUsingName(name);
    }
    @PostMapping("/add")
    public void save(PhongCach phongCachNoiThatEntity) {
        phongCachService.save(phongCachNoiThatEntity);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        phongCachService.deleteById(id);
    }
    @PutMapping("/update")
    public void update(@RequestBody PhongCach phongCachNoiThatEntity) {
        phongCachService.update(phongCachNoiThatEntity);
    }
    // Dont use this API yet
    @GetMapping("/fetch")
    public List<PhongCach> joinFetchPhongCach() {
        return phongCachService.joinFetchPhongCach();
    }
    @GetMapping("/fetch/{id}")
    public PhongCach joinFetchPhongCachUsingId(@PathVariable int id) {
        return phongCachService.joinFetchPhongCachUsingId(id);
    }
}
