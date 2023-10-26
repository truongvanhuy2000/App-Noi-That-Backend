package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.NoiThat;
import com.huy.backendnoithat.Service.ThongTinNoiThat.NoiThat.NoiThatService;
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
    public List<NoiThat> findAll(@RequestParam(value = "owner") String owner) {
        return noiThatService.findAll(owner);
    }
    @GetMapping("/{id}")
    public NoiThat findById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return noiThatService.findUsingId(owner, id);
    }
    @GetMapping("/search")
    public NoiThat findUsingName(@RequestParam(value = "owner") String owner, @RequestParam(value = "name") String name) {
        return noiThatService.findUsingName(owner, name);
    }
    @PostMapping("")
    public void save(@RequestParam(value = "owner") String owner, @RequestBody NoiThat noiThat, @RequestParam("parentId") int parentId) {
        noiThatService.save(owner, noiThat, parentId);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        noiThatService.deleteById(owner, id);
    }
    @PutMapping("")
    public void update(@RequestParam(value = "owner") String owner, @RequestBody NoiThat noiThat) {
        noiThatService.update(owner, noiThat);
    }
    @GetMapping("/searchByPhongCach/{id}")
    public List<NoiThat> searchByPhongCach(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return noiThatService.searchByPhongCach(owner, id);
    }
    // Dont use this API yet
    @GetMapping("/fetch")
    public List<NoiThat> joinFetchNoiThat(@RequestParam(value = "owner") String owner) {
        return noiThatService.joinFetchNoiThat();
    }
    @GetMapping("/fetch/{id}")
    public NoiThat joinFetchNoiThatUsingId(@RequestParam(value = "owner") String owner, @PathVariable int id) {
        return noiThatService.joinFetchNoiThatUsingId(id);
    }
    @GetMapping("/searchBy")
    public List<NoiThat> searchBy(@RequestParam(value = "owner") String owner,
                                           @RequestParam(value = "phongCachName") String phongCachName) {
        return noiThatService.searchBy(owner, phongCachName);
    }
    @GetMapping("/copySampleData")
    public ResponseEntity<String> copySampleDataFromAdmin(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                          @RequestParam(value = "parentId") int parentId) {
        String token = header.split(" ")[1].trim();
        noiThatService.copySampleDataFromAdmin(token, parentId);
        return ResponseEntity.ok("Copied successfully.");
    }
    @GetMapping("/swap")
    public void swap(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                     @RequestParam(value = "id1") int id1,
                     @RequestParam(value = "id2") int id2) {
        String token = header.split(" ")[1].trim();
        noiThatService.swap(token, id1, id2);
    }
}
