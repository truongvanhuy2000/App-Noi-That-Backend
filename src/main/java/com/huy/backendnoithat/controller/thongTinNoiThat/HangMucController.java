package com.huy.backendnoithat.controller.thongTinNoiThat;

import com.huy.backendnoithat.model.dto.BangNoiThat.HangMuc;
import com.huy.backendnoithat.service.thongTinNoiThat.HangMucService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hangmuc")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class HangMucController {
    private final HangMucService hangMucService;

    @GetMapping("")
    public List<HangMuc> findAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                 @RequestParam(value = "owner", required = false) String owner) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return hangMucService.findAll(token);
    }

    @GetMapping("/search")
    public HangMuc findUsingName(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                 @RequestParam(value = "owner", required = false) String owner,
                                 @RequestParam(value = "name") String name) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return hangMucService.findUsingName(token, name);
    }

    @GetMapping("/{id}")
    public HangMuc findById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                            @RequestParam(value = "owner", required = false) String owner, @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return hangMucService.findUsingId(token, id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                           @RequestParam(value = "owner", required = false) String owner, @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        hangMucService.deleteById(token, id);
    }

    @PutMapping("")
    public void update(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                       @RequestParam(value = "owner", required = false) String owner, @RequestBody HangMuc hangMuc) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        hangMucService.update(token, hangMuc);
    }

    @PostMapping("")
    public void save(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "owner", required = false) String owner,
                     @RequestBody HangMuc hangMuc,
                     @RequestParam("parentId") int parentId) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        hangMucService.save(token, hangMuc, parentId);
    }

    @GetMapping("/searchByNoiThat/{id}")
    public List<HangMuc> searchByNoiThat(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                         @RequestParam(value = "owner", required = false) String owner,
                                         @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return hangMucService.searchByNoiThat(token, id);
    }

    // Dont use this API yet
    @GetMapping("/fetch")
    public List<HangMuc> joinFetchHangMuc(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                          @RequestParam(value = "owner", required = false) String owner) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return hangMucService.joinFetchHangMuc();
    }

    @GetMapping("/fetch/{id}")
    public HangMuc joinFetchHangMucUsingId(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                           @RequestParam(value = "owner", required = false) String owner,
                                           @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return hangMucService.joinFetchHangMucUsingId(id);
    }

    @GetMapping("/searchBy")
    public List<HangMuc> searchBy(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                  @RequestParam(value = "owner", required = false) String owner,
                                  @RequestParam(value = "phongCachName") String phongCachName,
                                  @RequestParam(value = "noiThatName") String noiThatName) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return hangMucService.searchBy(token, phongCachName, noiThatName);
    }

    @GetMapping("/copySampleData")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                          @RequestParam(value = "parentId") int parentId) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        hangMucService.copySampleDataFromAdmin(token, parentId);
        return ResponseEntity.ok("Copied successfully.");
    }

    @GetMapping("/swap")
    public void swap(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "id1") int id1,
                     @RequestParam(value = "id2") int id2) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        hangMucService.swap(token, id1, id2);
    }
}
