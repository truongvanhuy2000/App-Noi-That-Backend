package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.ThongSo;
import com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo.ThongSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/thongso")
@RestController
public class ThongSoController {
    ThongSoService thongSoService;
    @Autowired
    public ThongSoController(ThongSoService thongSoService) {
        this.thongSoService = thongSoService;
    }
    @GetMapping("")
    public List<ThongSo> findAll() {
        return thongSoService.findAll();
    }
    @GetMapping("/search")
    public ThongSo findUsingName(@RequestParam String name) {
        return thongSoService.findUsingName(name);
    }
    @GetMapping("/{id}")
    public ThongSo findById(@PathVariable int id) {
        return thongSoService.findUsingId(id);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        thongSoService.deleteById(id);
    }
    @PostMapping("")
    public void save(@RequestBody ThongSo thongSo, @RequestParam("parentId") int parentId) {
        thongSoService.save(thongSo, parentId);
    }
    @PutMapping("")
    public void update(@RequestBody ThongSo thongSo) {
        thongSoService.update(thongSo);
    }
    @GetMapping("/searchByVatlieu/{id}")
    public List<ThongSo> searchByVatLieu(@PathVariable int id) {
        return thongSoService.searchByVatLieu(id);
    }
}
