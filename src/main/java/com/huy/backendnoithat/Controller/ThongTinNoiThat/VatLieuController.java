package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.AOP.DBModifyEvent;
import com.huy.backendnoithat.DTO.BangNoiThat.VatLieu;
import com.huy.backendnoithat.Service.ThongTinNoiThat.VatLieu.VatLieuService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
    public List<VatLieu> findAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                 @RequestParam(value = "owner", required = false) String owner) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return vatLieuService.findAll(token);
    }
    @GetMapping("/search")
    public VatLieu findUsingName(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                 @RequestParam(value = "owner", required = false) String owner,
                                 @RequestParam(value = "name") String name) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return vatLieuService.findUsingName(token, name);
    }
    @GetMapping("/{id}")
    public VatLieu findById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                            @RequestParam(value = "owner", required = false) String owner,
                            @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return vatLieuService.findUsingId(token, id);
    }
    @DeleteMapping("/{id}")
    @DBModifyEvent("VatLieu")
    public void deleteById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                           @RequestParam(value = "owner", required = false) String owner,
                           @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        vatLieuService.deleteById(token, id);
    }
    @PutMapping("")
    @DBModifyEvent("VatLieu")
    public void update(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                       @RequestParam(value = "owner", required = false) String owner,
                       @RequestBody VatLieu vatLieu) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        vatLieuService.update(token, vatLieu);
    }
    @PostMapping("")
    @DBModifyEvent("VatLieu")
    public void save(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "owner", required = false) String owner,
                     @RequestBody VatLieu vatLieu,
                     @RequestParam("parentId") int parentId) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        vatLieuService.save(token, vatLieu, parentId);
    }
    @GetMapping("/searchByHangMuc/{id}")
    public List<VatLieu> searchByHangMuc(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                         @RequestParam(value = "owner", required = false) String owner,
                                         @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return vatLieuService.searchByHangMuc(token, id);
    }
    @GetMapping("/searchBy")
    public List<VatLieu> searchBy(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                  @RequestParam(value = "owner", required = false) String owner,
                                  @RequestParam(value = "phongCachName") String phongCachName,
                                  @RequestParam(value = "noiThatName") String noiThatName,
                                  @RequestParam(value = "hangMucName") String hangMucName) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return vatLieuService.searchBy(token, phongCachName, noiThatName, hangMucName);
    }
    @GetMapping("/copySampleData")
    @DBModifyEvent("VatLieu")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                          @RequestParam(value = "parentId") int parentId) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        vatLieuService.copySampleDataFromAdmin(token, parentId);
        return ResponseEntity.ok("Copied successfully.");
    }
    @GetMapping("/swap")
    @DBModifyEvent("VatLieu")
    public void swap(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "id1") int id1,
                     @RequestParam(value = "id2") int id2) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        vatLieuService.swap(token, id1, id2);
    }
}
