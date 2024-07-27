package com.huy.backendnoithat.service.general;

import com.huy.backendnoithat.model.dto.AccountManagement.Account;

import java.util.Optional;
import java.util.UUID;

public interface JwtTokenService {
    String generateAccessToken(String username, UUID refreshTokenID);
    boolean verifyAccessToken(String token);
    Optional<String> getUsernameFromToken(String token);
    boolean verifyRefreshToken(String refreshToken);
    String generateRefreshToken(Account account);

    void revokeRefreshToken(String username);

    String generateRefreshToken(Account account, UUID tokenID);

    Optional<UUID> getTokenIdFromToken(String token);
}
