package com.huy.backendnoithat.Controller.ThongTinNoiTHat;

import com.huy.backendnoithat.Entity.ThongSo;
import com.huy.backendnoithat.Service.ThongTinNoiThat.Interface.ThongSoService;
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
    public void save(ThongSo thongSo) {
        thongSoService.save(thongSo);
    }
    @PutMapping("/update")
    public void update(ThongSo thongSo) {
        thongSoService.update(thongSo);
    }
}
