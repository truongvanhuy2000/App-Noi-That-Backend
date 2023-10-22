package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.DTO.BangNoiThat.ThongSo;
import com.huy.backendnoithat.Service.ThongTinNoiThat.ThongSo.ThongSoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
    public List<ThongSo> findAll(@RequestParam(value = "owner") String owner) {
        return thongSoService.findAll(owner);
    }
    @GetMapping("/search")
    public ThongSo findUsingName(@RequestParam(value = "owner") String owner, @RequestParam String name) {
        return thongSoService.findUsingName(owner, name);
    }
    @GetMapping("/{id}")
    public ThongSo findById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return thongSoService.findUsingId(owner, id);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        thongSoService.deleteById(owner, id);
    }
    @PostMapping("")
    public void save(@RequestParam(value = "owner") String owner, @RequestBody ThongSo thongSo, @RequestParam("parentId") int parentId) {
        thongSoService.save(owner, thongSo, parentId);
    }
    @PutMapping("")
    public void update(@RequestParam(value = "owner") String owner, @RequestBody ThongSo thongSo) {
        thongSoService.update(owner, thongSo);
    }
    @GetMapping("/searchByVatlieu/{id}")
    public List<ThongSo> searchByVatLieu(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return thongSoService.searchByVatLieu(owner, id);
    }
    @GetMapping("/seachByParentName")
    public List<NoiThat> seachByParentName(@RequestParam(value = "owner") String owner,
                                           @RequestParam(value = "phongCachName") String phongCachName,
                                           @RequestParam(value = "noiThatName") String noiThatName,
                                           @RequestParam(value = "hangMucName") String hangMucName,
                                           @RequestParam(value = "vatLieuName") String vatLieuName) {
        return null;
    }
    @GetMapping("/copySampleData")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                          @RequestParam(value = "parentId") int parentId) {
        String token = header.split(" ")[1].trim();
        thongSoService.copySampleDataFromAdmin(token, parentId);
        return ResponseEntity.ok("Copied successfully.");
    }
}
