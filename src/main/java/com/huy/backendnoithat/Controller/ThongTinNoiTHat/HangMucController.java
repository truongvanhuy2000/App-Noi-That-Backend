package com.huy.backendnoithat.Controller.ThongTinNoiTHat;

import com.huy.backendnoithat.Entity.HangMucEntity;
import com.huy.backendnoithat.DataModel.HangMuc;
import com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc.HangMucService;
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
    public void update(HangMucEntity hangMucEntity) {
        hangMucService.update(hangMucEntity);
    }
    @PostMapping("/add")
    public void save(HangMucEntity hangMucEntity) {
        hangMucService.save(hangMucEntity);
    }
    @GetMapping("/searchByNoiThat/{id}")
    public List<HangMuc> searchByNoiThat(@PathVariable int id) {
        return hangMucService.searchByNoiThat(id);
    }
    // Dont use this API yet
    @GetMapping("/fetch")
    public List<HangMuc> joinFetchHangMuc() {
        return hangMucService.joinFetchHangMuc();
    }
    @GetMapping("/fetch/{id}")
    public HangMuc joinFetchHangMucUsingId(@PathVariable int id) {
        return hangMucService.joinFetchHangMucUsingId(id);
    }
}
