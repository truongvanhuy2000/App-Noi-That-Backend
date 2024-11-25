package com.huy.backendnoithat.controller.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.VatLieu;
import com.huy.backendnoithat.service.thongTinNoiThat.VatLieuService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vatlieu")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class VatLieuController {
    private final VatLieuService vatLieuService;

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
    public void deleteById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                           @RequestParam(value = "owner", required = false) String owner,
                           @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        vatLieuService.deleteById(token, id);
    }

    @PutMapping("")
    public void update(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                       @RequestParam(value = "owner", required = false) String owner,
                       @RequestBody VatLieu vatLieu) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        vatLieuService.update(token, vatLieu);
    }

    @PostMapping("")
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
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                          @RequestParam(value = "parentId") int parentId) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        vatLieuService.copySampleDataFromAdmin(token, parentId);
        return ResponseEntity.ok("Copied successfully.");
    }

    @GetMapping("/swap")
    public void swap(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "id1") int id1,
                     @RequestParam(value = "id2") int id2) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        vatLieuService.swap(token, id1, id2);
    }
}
