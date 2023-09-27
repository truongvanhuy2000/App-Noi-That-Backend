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
    public List<VatLieu> findAll(@RequestParam(value = "owner") String owner) {
        return vatLieuService.findAll(owner);
    }
    @GetMapping("/search")
    public VatLieu findUsingName(@RequestParam(value = "owner") String owner, @RequestParam String name) {
        return vatLieuService.findUsingName(owner, name);
    }
    @GetMapping("/{id}")
    public VatLieu findById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return vatLieuService.findUsingId(owner, id);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        vatLieuService.deleteById(owner, id);
    }
    @PutMapping("")
    public void update(@RequestParam(value = "owner") String owner, @RequestBody VatLieu vatLieu) {
        vatLieuService.update(owner, vatLieu);
    }
    @PostMapping("")
    public void save(@RequestParam(value = "owner") String owner, @RequestBody VatLieu vatLieu, @RequestParam("parentId") int parentId) {
        vatLieuService.save(owner, vatLieu, parentId);
    }
    @GetMapping("/searchByHangMuc/{id}")
    public List<VatLieu> searchByHangMuc(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return vatLieuService.searchByHangMuc(owner, id);
    }
}
