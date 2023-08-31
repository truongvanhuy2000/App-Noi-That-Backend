package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;
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
    public HangMuc findUsingName(@RequestParam String name) {
        return hangMucService.findUsingName(name);
    }
    @GetMapping("/{id}")
    public HangMuc findById(@PathVariable int id) {
        return hangMucService.findUsingId(id);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        hangMucService.deleteById(id);
    }
    @PutMapping("")
    public void update(@RequestBody HangMuc hangMuc) {
        hangMucService.update(hangMuc);
    }
    @PostMapping("")
    public void save(@RequestBody HangMuc hangMuc, @RequestParam("parentId") int parentId) {
        hangMucService.save(hangMuc, parentId);
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
