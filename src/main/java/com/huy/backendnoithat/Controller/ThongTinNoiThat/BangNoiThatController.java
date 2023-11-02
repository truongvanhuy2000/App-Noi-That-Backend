package com.huy.backendnoithat.Controller.ThongTinNoiThat;

import com.huy.backendnoithat.Service.ThongTinNoiThat.BangNoiThatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bangnoithat")
public class BangNoiThatController {
    BangNoiThatService bangNoiThatService;
    @Autowired
    public BangNoiThatController(BangNoiThatService bangNoiThatService) {
        this.bangNoiThatService = bangNoiThatService;
    }
    @GetMapping("/sampleAll")
    public void sampleAll(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String token = header.split(" ")[1].trim();
        bangNoiThatService.sampleAll(token);
    }
}
