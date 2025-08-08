package com.huy.backendnoithat.controller.v0.account;

import com.huy.backendnoithat.model.RefreshTokenRequest;
import com.huy.backendnoithat.model.dto.LoginRequest;
import com.huy.backendnoithat.model.dto.TokenResponse;
import com.huy.backendnoithat.service.v0.account.LoginService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@Hidden
@Deprecated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginController {
    private final LoginService loginService;

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest requestBody) {
        String username = requestBody.getUsername();
        String password = requestBody.getPassword();
        try {
            TokenResponse token = loginService.login(username, password);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/refreshToken", produces = "application/json")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest requestBody) throws AuthenticationException {
        String refreshToken = requestBody.getRefreshToken();
        TokenResponse tokenResponse = loginService.refreshToken(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }

}
