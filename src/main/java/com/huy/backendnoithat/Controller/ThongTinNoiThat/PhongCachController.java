package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.AOP.DBModifyEvent;
import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.Service.ThongTinNoiThat.PhongCach.PhongCachService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
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
    @CrossOrigin
    public List<PhongCach> findAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                   @RequestParam(value = "owner", required = false) String owner) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return phongCachService.findAll(token);
    }
    @GetMapping("/{id}")
    public PhongCach findById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                              @RequestParam(value = "owner", required = false) String owner, @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return phongCachService.findById(token, id);
    }
    @GetMapping("/search")
    public PhongCach findUsingName(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                   @RequestParam(value = "owner", required = false) String owner,
                                   @RequestParam(value = "name") String name) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return phongCachService.findUsingName(token, name);
    }
    @PostMapping("")
    @DBModifyEvent("PhongCach")
    public void save(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "owner", required = false) String owner, @RequestBody PhongCach phongCach) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        phongCachService.save(token, phongCach);
    }
    @DeleteMapping("/{id}")
    @DBModifyEvent("PhongCach")
    public void deleteById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                           @RequestParam(value = "owner", required = false) String owner, @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        phongCachService.deleteById(token, id);
    }
    @PutMapping("")
    @DBModifyEvent("PhongCach")
    public void update(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                       @RequestParam(value = "owner", required = false) String owner, @RequestBody PhongCach phongCach) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        phongCachService.update(token, phongCach);
    }

    // Don't use this API yet
//    @GetMapping("/fetch")
//    public List<PhongCach> joinFetchPhongCach(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
//                                              @RequestParam(value = "owner", required = false) String owner) {
//        String token = JwtTokenUtil.getTokenFromHeader(header);
//        return phongCachService.joinFetchPhongCach(token);
//    }
//    @GetMapping("/fetch/{id}")
//    public PhongCach joinFetchPhongCachUsingId(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
//                                               @RequestParam(value = "owner", required = false) String owner,
//                                               @PathVariable int id) {
//        String token = JwtTokenUtil.getTokenFromHeader(header);
//        return phongCachService.joinFetchPhongCachUsingId(token, id);
//    }
    @GetMapping("/copySampleData")
    @DBModifyEvent("PhongCach")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        phongCachService.copySampleDataFromAdmin(token);
        return ResponseEntity.ok("Copied successfully.");
    }
    @GetMapping("/swap")
    @DBModifyEvent("PhongCach")
    public void swap(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "id1") int id1,
                     @RequestParam(value = "id2") int id2) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        phongCachService.swap(token, id1, id2);
    }
}
