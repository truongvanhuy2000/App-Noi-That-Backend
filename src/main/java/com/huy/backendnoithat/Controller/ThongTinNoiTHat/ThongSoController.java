package com.huy.backendnoithat.Controller.ThongTinNoiTHat;

import com.huy.backendnoithat.Entity.ThongSoEntity;
import com.huy.backendnoithat.DataModel.ThongSo;
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
    public ThongSo findUsingName(String name) {
        return thongSoService.findUsingName(name);
    }
    @GetMapping("{id}")
    public ThongSo findById(int id) {
        return thongSoService.findUsingId(id);
    }
    @DeleteMapping("{id}")
    public void deleteById(int id) {
        thongSoService.deleteById(id);
    }
    @PostMapping("/add")
    public void save(ThongSoEntity thongSoEntity) {
        thongSoService.save(thongSoEntity);
    }
    @PutMapping("/update")
    public void update(ThongSoEntity thongSoEntity) {
        thongSoService.update(thongSoEntity);
    }
    @GetMapping("/searchByVatlieu/{id}")
    public List<ThongSo> searchByVatLieu(@PathVariable int id) {
        return thongSoService.searchByVatLieu(id);
    }
}
