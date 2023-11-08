package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.AOP.DBModifyEvent;
import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat.NoiThatService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/noithat")
@RestController
public class NoiThatController {
    NoiThatService noiThatService;
    @Autowired
    public NoiThatController(NoiThatService noiThatService) {
        this.noiThatService = noiThatService;
    }
    @GetMapping("")
    public List<NoiThat> findAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                 @RequestParam(value = "owner", required = false) String owner) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return noiThatService.findAll(token);
    }
    @GetMapping("/{id}")
    public NoiThat findById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                            @RequestParam(value = "owner", required = false) String owner, @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return noiThatService.findUsingId(token, id);
    }
    @GetMapping("/search")
    public NoiThat findUsingName(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                 @RequestParam(value = "owner", required = false) String owner,
                                 @RequestParam(value = "name") String name) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return noiThatService.findUsingName(token, name);
    }
    @PostMapping("")
    @DBModifyEvent("NoiThat")
    public void save(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "owner", required = false) String owner,
                     @RequestBody NoiThat noiThat, @RequestParam("parentId") int parentId) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        noiThatService.save(token, noiThat, parentId);
    }
    @DeleteMapping("/{id}")
    @DBModifyEvent("NoiThat")
    public void deleteById(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                           @RequestParam(value = "owner", required = false) String owner,
                           @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        noiThatService.deleteById(token, id);
    }
    @PutMapping("")
    @DBModifyEvent("NoiThat")
    public void update(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                       @RequestParam(value = "owner", required = false) String owner,
                       @RequestBody NoiThat noiThat) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        noiThatService.update(token, noiThat);
    }
    @GetMapping("/searchByPhongCach/{id}")
    public List<NoiThat> searchByPhongCach(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                           @RequestParam(value = "owner", required = false) String owner,
                                           @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return noiThatService.searchByPhongCach(token, id);
    }
    // Dont use this API yet
    @GetMapping("/fetch")
    public List<NoiThat> joinFetchNoiThat(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                          @RequestParam(value = "owner", required = false) String owner) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return noiThatService.joinFetchNoiThat();
    }
    @GetMapping("/fetch/{id}")
    public NoiThat joinFetchNoiThatUsingId(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                           @RequestParam(value = "owner", required = false) String owner,
                                           @PathVariable int id) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return noiThatService.joinFetchNoiThatUsingId(id);
    }
    @GetMapping("/searchBy")
    public List<NoiThat> searchBy(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                  @RequestParam(value = "owner", required = false) String owner,
                                  @RequestParam(value = "phongCachName") String phongCachName) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return noiThatService.searchBy(token, phongCachName);
    }
    @GetMapping("/copySampleData")
    @DBModifyEvent("NoiThat")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                          @RequestParam(value = "parentId") int parentId) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        noiThatService.copySampleDataFromAdmin(token, parentId);
        return ResponseEntity.ok("Copied successfully.");
    }
    @GetMapping("/swap")
    @DBModifyEvent("NoiThat")
    public void swap(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "id1") int id1,
                     @RequestParam(value = "id2") int id2) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        noiThatService.swap(token, id1, id2);
    }
}
