package com.huy.backendnoithat.service.general;

import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.enums.UserRole;
import jakarta.servlet.http.Cookie;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface JwtTokenService {
    String TOKEN_COOKIE = "token";
    String REFRESH_TOKEN_COOKIE = "refresh_token";
    long JWT_TOKEN_VALIDITY_MILLIS = TimeUnit.HOURS.toMillis(1);
    long JWT_REFRESH_TOKEN_VALIDITY_MILLIS = TimeUnit.DAYS.toMillis(30);

    String generateAccessToken(Long userId, String username, List<String> roles);

    Optional<String> getUsernameFromToken(String token);

    boolean verifyAccessToken(String token);

    Optional<String> getSubjectFromToken(String token);

    List<String> getUserRolesFromToken(String token);

    Optional<Long> getUserIdFromToken(String token);

    boolean verifyRefreshToken(String refreshToken);

    String generateRefreshToken(String username);

    Optional<Date> getExpirationDateFromToken(String token);
}
