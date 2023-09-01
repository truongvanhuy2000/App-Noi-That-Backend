package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.DTO.BangNoiThat.PhongCach;
import com.huy.backendnoithat.Entity.BangNoiThat.PhongCachNoiThatEntity;
import com.huy.backendnoithat.Service.ThongTinNoiThat.BangNoiThatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bangnoithat")
public class BangNoiThatController {
    BangNoiThatService bangNoiThatService;
    @Autowired
    public BangNoiThatController(BangNoiThatService bangNoiThatService) {
        this.bangNoiThatService = bangNoiThatService;
    }
    @GetMapping("/fetchAll")
    public List<PhongCachNoiThatEntity> fetchAll() {
        return bangNoiThatService.fetchAll();
    }
}
