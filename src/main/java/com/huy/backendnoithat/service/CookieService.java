package com.huy.backendnoithat.service;

import com.huy.backendnoithat.service.general.JwtTokenService;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CookieService {
    @Value("${deployment.secure}")
    private boolean secure;

    public Cookie generateTokenCookie(String token, long duration) {
        Cookie tokenCookie = new Cookie(JwtTokenService.TOKEN_COOKIE, token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setSecure(secure);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(duration));
        return tokenCookie;
    }

    public Cookie generateRefreshTokenCookie(String refreshToken, long duration) {
        Cookie refreshTokenCookie = new Cookie(JwtTokenService.REFRESH_TOKEN_COOKIE, refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(secure);
        refreshTokenCookie.setPath("/api/v1/public/refresh");
        refreshTokenCookie.setMaxAge((int) TimeUnit.MILLISECONDS.toSeconds(duration));
        return refreshTokenCookie;
    }
}
