package com.huy.backendnoithat.controller.thongTinNoiThat;

import com.huy.backendnoithat.aop.DBModifyEvent;
import com.huy.backendnoithat.model.dto.BangNoiThat.NoiThat;
import com.huy.backendnoithat.model.dto.BangNoiThat.ThongSo;
import com.huy.backendnoithat.service.thongTinNoiThat.ThongSoService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/thongso")
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ThongSoController {
    private final ThongSoService thongSoService;

    @GetMapping("")
    public List<ThongSo> findAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                 @RequestParam(value = "owner", required = false) String owner) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return thongSoService.findAll(token);
    }

    @GetMapping("/search")
    public ThongSo findUsingName(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                 @RequestParam(value = "owner", required = false) String owner,
                                 @RequestParam String name) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return thongSoService.findUsingName(token, name);
    }

    @GetMapping("/{id}")
    public ThongSo findById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                            @RequestParam(value = "owner", required = false) String owner,
                            @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return thongSoService.findUsingId(token, id);
    }

    @DeleteMapping("/{id}")
    @DBModifyEvent("ThongSo")
    public void deleteById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                           @RequestParam(value = "owner", required = false) String owner,
                           @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        thongSoService.deleteById(token, id);
    }

    @PostMapping("")
    @DBModifyEvent("ThongSo")
    public void save(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "owner", required = false) String owner,
                     @RequestBody ThongSo thongSo, @RequestParam("parentId") int parentId) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        thongSoService.save(token, thongSo, parentId);
    }

    @PutMapping("")
    @DBModifyEvent("ThongSo")
    public void update(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                       @RequestParam(value = "owner", required = false) String owner,
                       @RequestBody ThongSo thongSo) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        thongSoService.update(token, thongSo);
    }

    @GetMapping("/searchByVatlieu/{id}")
    public List<ThongSo> searchByVatLieu(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                         @RequestParam(value = "owner", required = false) String owner,
                                         @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return thongSoService.searchByVatLieu(token, id);
    }

    @GetMapping("/seachByParentName")
    public List<NoiThat> seachByParentName(@RequestParam(value = "owner", required = false) String owner,
                                           @RequestParam(value = "phongCachName") String phongCachName,
                                           @RequestParam(value = "noiThatName") String noiThatName,
                                           @RequestParam(value = "hangMucName") String hangMucName,
                                           @RequestParam(value = "vatLieuName") String vatLieuName) {
        return null;
    }

    @GetMapping("/copySampleData")
    @DBModifyEvent("ThongSo")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                          @RequestParam(value = "parentId") int parentId) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        thongSoService.copySampleDataFromAdmin(token, parentId);
        return ResponseEntity.ok("Copied successfully.");
    }
}
