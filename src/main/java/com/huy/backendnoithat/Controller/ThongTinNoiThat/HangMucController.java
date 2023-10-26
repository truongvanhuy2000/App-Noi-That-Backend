package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.HangMuc;
import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.Service.ThongTinNoiThat.HangMuc.HangMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
    public List<HangMuc> findAll(@RequestParam(value = "owner") String owner) {
        return hangMucService.findAll(owner);
    }
    @GetMapping("/search")
    public HangMuc findUsingName(@RequestParam(value = "owner") String owner, @RequestParam(value = "name") String name) {
        return hangMucService.findUsingName(owner, name);
    }
    @GetMapping("/{id}")
    public HangMuc findById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return hangMucService.findUsingId(owner, id);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        hangMucService.deleteById(owner, id);
    }
    @PutMapping("")
    public void update(@RequestParam(value = "owner") String owner, @RequestBody HangMuc hangMuc) {
        hangMucService.update(owner, hangMuc);
    }
    @PostMapping("")
    public void save(@RequestParam(value = "owner") String owner, @RequestBody HangMuc hangMuc, @RequestParam("parentId") int parentId) {
        hangMucService.save(owner, hangMuc, parentId);
    }
    @GetMapping("/searchByNoiThat/{id}")
    public List<HangMuc> searchByNoiThat(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return hangMucService.searchByNoiThat(owner, id);
    }
    // Dont use this API yet
    @GetMapping("/fetch")
    public List<HangMuc> joinFetchHangMuc(@RequestParam(value = "owner") String owner) {
        return hangMucService.joinFetchHangMuc();
    }
    @GetMapping("/fetch/{id}")
    public HangMuc joinFetchHangMucUsingId(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return hangMucService.joinFetchHangMucUsingId(id);
    }
    @GetMapping("/searchBy")
    public List<HangMuc> searchBy(@RequestParam(value = "owner") String owner,
                                           @RequestParam(value = "phongCachName") String phongCachName,
                                           @RequestParam(value = "noiThatName") String noiThatName) {
        return hangMucService.searchBy(owner, phongCachName, noiThatName);
    }
    @GetMapping("/copySampleData")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                          @RequestParam(value = "parentId") int parentId) {
        String token = header.split(" ")[1].trim();
        hangMucService.copySampleDataFromAdmin(token, parentId);
        return ResponseEntity.ok("Copied successfully.");
    }
    @GetMapping("/swap")
    public void swap(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "id1") int id1,
                     @RequestParam(value = "id2") int id2) {
        String token = header.split(" ")[1].trim();
        hangMucService.swap(token, id1, id2);
    }
}
