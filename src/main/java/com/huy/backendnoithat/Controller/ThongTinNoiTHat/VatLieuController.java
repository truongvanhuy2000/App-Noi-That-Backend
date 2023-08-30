package com.huy.backendnoithat.Controller.ThongTinNoiTHat;

import com.huy.backendnoithat.Entity.VatLieuEntity;
import com.huy.backendnoithat.DataModel.VatLieu;
import com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu.VatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vatlieu")
public class VatLieuController {
    VatLieuService vatLieuService;
    @Autowired
    public VatLieuController(VatLieuService vatLieuService) {
        this.vatLieuService = vatLieuService;
    }
    @GetMapping("")
    public List<VatLieu> findAll() {
        return vatLieuService.findAll();
    }
    @GetMapping("/search")
    public VatLieu findUsingName(String name) {
        return vatLieuService.findUsingName(name);
    }
    @GetMapping("{id}")
    public VatLieu findById(int id) {
        return vatLieuService.findUsingId(id);
    }
    @DeleteMapping("{id}")
    public void deleteById(int id) {
        vatLieuService.deleteById(id);
    }
    @PutMapping("/update")
    public void update(VatLieuEntity vatLieuEntity) {
        vatLieuService.update(vatLieuEntity);
    }
    @PostMapping("/add")
    public void save(VatLieuEntity vatLieuEntity) {
        vatLieuService.save(vatLieuEntity);
    }
    @GetMapping("/searchByHangMuc/{id}")
    public List<VatLieu> searchByHangMuc(@PathVariable int id) {
        return vatLieuService.searchByHangMuc(id);
    }
}
