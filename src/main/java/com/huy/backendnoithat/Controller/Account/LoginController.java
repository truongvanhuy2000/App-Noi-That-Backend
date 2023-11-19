package com.huy.backendnoithat.Controller.Account;

import com.huy.backendnoithat.DTO.TokenResponse;
import com.huy.backendnoithat.Service.Account.LoginService;
import com.huy.backendnoithat.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<TokenResponse> login(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        TokenResponse token = loginService.login(username, password);
        return ResponseEntity.ok(token);
    }
    @PostMapping(value = "/refreshToken", produces = "application/json")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody Map<String, String> requestBody) {
        String refreshToken = requestBody.get("refreshToken");
        TokenResponse tokenResponse = loginService.refreshToken(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }

}
