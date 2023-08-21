package com.huy.backendnoithat.Controller.ThongTinNoiTHat;

import com.huy.backendnoithat.Entity.HangMuc;
import com.huy.backendnoithat.Service.ThongTinNoiThat.Interface.HangMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hangmuc")
public class HangMucController {
    HangMucService hangMucService;
    @Autowired
    public HangMucController(HangMucService hangMucService) {
        this.hangMucService = hangMucService;
    }
    @GetMapping("")
    public List<HangMuc> findAll() {
        return hangMucService.findAll();
    }
    @GetMapping("/search")
    public HangMuc findUsingName(String name) {
        return hangMucService.findUsingName(name);
    }
    @GetMapping("{id}")
    public HangMuc findById(int id) {
        return hangMucService.findUsingId(id);
    }
    @DeleteMapping("{id}")
    public void deleteById(int id) {
        hangMucService.deleteById(id);
    }
    @PutMapping("/update")
    public void update(HangMuc hangMuc) {
        hangMucService.update(hangMuc);
    }
    @PostMapping("/add")
    public void save(HangMuc hangMuc) {
        hangMucService.save(hangMuc);
    }
}