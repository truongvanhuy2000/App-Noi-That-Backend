package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach.PhongCachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/phongcach")
@RestController
public class PhongCachController {
    PhongCachService phongCachService;
    @Autowired
    public PhongCachController(PhongCachService phongCachService) {
        this.phongCachService = phongCachService;
    }
    @GetMapping("")
    public List<PhongCach> findAll(@RequestParam(value = "owner") String owner) {
        return phongCachService.findAll(owner);
    }
    @GetMapping("/{id}")
    public PhongCach findById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return phongCachService.findById(owner, id);
    }
    @GetMapping("/search")
    public PhongCach findUsingName(@RequestParam(value = "owner") String owner, @RequestParam(value = "name") String name) {
        return phongCachService.findUsingName(owner, name);
    }
    @PostMapping("")
    public void save(@RequestParam(value = "owner") String owner, @RequestBody PhongCach phongCach) {
        phongCachService.save(owner, phongCach);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        phongCachService.deleteById(owner, id);
    }
    @PutMapping("")
    public void update(@RequestParam(value = "owner") String owner, @RequestBody PhongCach phongCach) {
        phongCachService.update(owner, phongCach);
    }

    // Don't use this API yet
    @GetMapping("/fetch")
    public List<PhongCach> joinFetchPhongCach(@RequestParam(value = "owner") String owner) {
        return phongCachService.joinFetchPhongCach(owner);
    }
    @GetMapping("/fetch/{id}")
    public PhongCach joinFetchPhongCachUsingId(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return phongCachService.joinFetchPhongCachUsingId(owner, id);
    }
    @GetMapping("/copySampleData")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String token = header.split(" ")[1].trim();
        phongCachService.copySampleDataFromAdmin(token);
        return ResponseEntity.ok("Copied successfully.");
    }
    @GetMapping("/swap")
    public void swap(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "id1") int id1,
                     @RequestParam(value = "id2") int id2) {
        String token = header.split(" ")[1].trim();
        phongCachService.swap(token, id1, id2);
    }
}
