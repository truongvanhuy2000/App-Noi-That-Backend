package com.huy.backendnoithat.controller.v1.publics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huy.backendnoithat.exception.AuthorizationException;
import com.huy.backendnoithat.model.RefreshTokenRequest;
import com.huy.backendnoithat.model.dto.LoginRequest;
import com.huy.backendnoithat.model.dto.TokenResponse;
import com.huy.backendnoithat.service.CookieService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.service.v0.account.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthenticationController {
    private final LoginService loginService;
    private final CookieService cookieService;

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<TokenResponse> login(
        @RequestBody LoginRequest requestBody,
        HttpServletResponse response
    ) throws AuthenticationException {
        String username = requestBody.getUsername();
        String password = requestBody.getPassword();
        TokenResponse token = loginService.login(username, password);
        Cookie tokenCookie = cookieService.generateTokenCookie(
            token.getAccessToken(), JwtTokenService.JWT_TOKEN_VALIDITY_MILLIS);
        Cookie refreshTokenCookie = cookieService.generateRefreshTokenCookie(
            token.getRefreshToken(), JwtTokenService.JWT_REFRESH_TOKEN_VALIDITY_MILLIS);
        response.addCookie(tokenCookie);
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/login/digital-signature", produces = "application/json", consumes = "text/plain")
    public ResponseEntity<TokenResponse> loginWithDigitalSignature(
        @RequestBody String digitalSignature, HttpServletResponse response
    ) throws JsonProcessingException, AuthenticationException {
        TokenResponse token = loginService.parseDigitalSignature(digitalSignature);
        Cookie tokenCookie = cookieService.generateTokenCookie(
            token.getAccessToken(), JwtTokenService.JWT_TOKEN_VALIDITY_MILLIS);
        Cookie refreshTokenCookie = cookieService.generateRefreshTokenCookie(
            token.getRefreshToken(), JwtTokenService.JWT_REFRESH_TOKEN_VALIDITY_MILLIS);
        response.addCookie(tokenCookie);
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/refresh", produces = "application/json")
    public ResponseEntity<TokenResponse> refreshToken(
        @RequestBody(required = false) RefreshTokenRequest requestBody,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticationException {
        String refreshToken;
        if (requestBody != null && requestBody.getRefreshToken() != null) {
            refreshToken = requestBody.getRefreshToken();
        } else {
            if (request.getCookies() == null) {
                throw new AuthorizationException("No cookies found in request");
            }
            Optional<Cookie> refreshTokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(JwtTokenService.REFRESH_TOKEN_COOKIE)).findFirst();
            if (refreshTokenCookie.isEmpty()) {
                throw new AuthorizationException("No refresh token found in cookies");
            }
            refreshToken = refreshTokenCookie.get().getValue();
        }
        TokenResponse tokenResponse = loginService.refreshToken(refreshToken);
        Cookie tokenCookie = cookieService.generateTokenCookie(
            tokenResponse.getAccessToken(), JwtTokenService.JWT_TOKEN_VALIDITY_MILLIS);
        response.addCookie(tokenCookie);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie tokenCookie = cookieService.generateTokenCookie("", 0);
        Cookie refreshTokenCookie = cookieService.generateRefreshTokenCookie("", 0);
        response.addCookie(tokenCookie);
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok().build();
    }

}
