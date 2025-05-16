package com.huy.backendnoithat.controller.v0.account;

import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.AccountManagement.AccountInformation;
import com.huy.backendnoithat.service.v0.account.BaseService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BaseController {
    private final BaseService baseService;

    @GetMapping("/index")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/info")
    public Account info(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                        @RequestParam(name = "username", required = false) String username) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        return baseService.getAccountInformation(token);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                 @RequestBody Map<String, String> requestBody) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        baseService.changePassword(token, requestBody);
        return ResponseEntity.ok("Change password successfully");
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<String> updateInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                             @RequestBody AccountInformation AccountInfo) {
        String token = JwtTokenUtil.getTokenFromHeader(header);
        baseService.updateInfo(token, AccountInfo);
        return ResponseEntity.ok("Updated successfully");
    }

}
