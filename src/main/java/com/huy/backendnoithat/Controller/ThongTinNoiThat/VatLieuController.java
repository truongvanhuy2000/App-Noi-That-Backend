package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;
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
    public VatLieu findUsingName(@RequestParam String name) {
        return vatLieuService.findUsingName(name);
    }
    @GetMapping("/{id}")
    public VatLieu findById(@PathVariable int id) {
        return vatLieuService.findUsingId(id);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        vatLieuService.deleteById(id);
    }
    @PutMapping("")
    public void update(@RequestBody VatLieu vatLieu) {
        vatLieuService.update(vatLieu);
    }
    @PostMapping("")
    public void save(@RequestBody VatLieu vatLieu, @RequestParam("parentId") int parentId) {
        vatLieuService.save(vatLieu, parentId);
    }
    @GetMapping("/searchByHangMuc/{id}")
    public List<VatLieu> searchByHangMuc(@PathVariable int id) {
        return vatLieuService.searchByHangMuc(id);
    }
}
