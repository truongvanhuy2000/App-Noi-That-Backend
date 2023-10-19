package com.huy.backendnoithat.Controller.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.Service.Account.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BaseController {
    private BaseService baseService;
    @Autowired
    public BaseController(BaseService baseService) {
        this.baseService = baseService;
    }
    @GetMapping("/index")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Hello");
    }
    @GetMapping("/info")
    public Account info(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                        @RequestParam(name="username", required = false) String username) {
        String token = header.split(" ")[1].trim();
        return baseService.getAccountInformation(token);
    }

}
