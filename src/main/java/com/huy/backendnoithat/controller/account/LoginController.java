package com.huy.backendnoithat.controller.account;

import com.huy.backendnoithat.model.dto.TokenResponse;
import com.huy.backendnoithat.service.account.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginController {
    private final LoginService loginService;

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
