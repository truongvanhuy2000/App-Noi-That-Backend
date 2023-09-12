package com.huy.backendnoithat.Controller.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.Service.Account.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Account info(@RequestParam(name="username") String username) {
        return baseService.getAccountInformation(username);
    }
}
