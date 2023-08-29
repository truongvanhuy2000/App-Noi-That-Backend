package com.huy.backendnoithat.Controller.ThongTinNoiTHat;

import com.huy.backendnoithat.Entity.VatLieu;
import com.huy.backendnoithat.Response.VatLieuResponse;
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
    public List<VatLieuResponse> findAll() {
        return vatLieuService.findAll();
    }
    @GetMapping("/search")
    public VatLieuResponse findUsingName(String name) {
        return vatLieuService.findUsingName(name);
    }
    @GetMapping("{id}")
    public VatLieuResponse findById(int id) {
        return vatLieuService.findUsingId(id);
    }
    @DeleteMapping("{id}")
    public void deleteById(int id) {
        vatLieuService.deleteById(id);
    }
    @PutMapping("/update")
    public void update(VatLieu vatLieu) {
        vatLieuService.update(vatLieu);
    }
    @PostMapping("/add")
    public void save(VatLieu vatLieu) {
        vatLieuService.save(vatLieu);
    }

}
