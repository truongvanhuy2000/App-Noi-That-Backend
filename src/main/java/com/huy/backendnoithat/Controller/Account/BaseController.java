package com.huy.backendnoithat.Controller.Account;

import com.huy.backendnoithat.DTO.AccountManagement.Account;
import com.huy.backendnoithat.DTO.AccountManagement.AccountInformation;
import com.huy.backendnoithat.DTO.TokenResponse;
import com.huy.backendnoithat.Service.Account.BaseService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return baseService.getAccountInformation(token);
    }
    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                 @RequestBody Map<String, String> requestBody) {
        String token = JwtTokenUtil.getTokenFromHeader(header);;
        baseService.changePassword(token, requestBody);
        return ResponseEntity.ok("Change password successfully");
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<String> updateInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                         @RequestBody AccountInformation AccountInfo) {
        String token = JwtTokenUtil.getTokenFromHeader(header);;
        baseService.updateInfo(token, AccountInfo);
        return ResponseEntity.ok("Updated successfully");
    }

}
