package com.huy.backendnoithat.service.account.impl;

import com.huy.backendnoithat.exception.AccountExpiredException;
import com.huy.backendnoithat.exception.AccountIsDisabledException;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.TokenResponse;
import com.huy.backendnoithat.service.account.AccountService;
import com.huy.backendnoithat.service.account.LoginService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final AccountService accountService;

    @Override
    public TokenResponse login(String username, String password) {
        Account account = accountService.findByUsername(username);
        if (account.getExpiredDate().isBefore(LocalDate.now())) {
            throw new AccountExpiredException("Account is expired");
        }
        if (!account.isEnabled() || !account.isActive()) {
            throw new AccountIsDisabledException("Account is disabled");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UUID refreshTokenID = UUID.randomUUID();
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setRefreshToken(jwtTokenService.generateRefreshToken(account, refreshTokenID));
        tokenResponse.setToken(jwtTokenService.generateAccessToken(account.getUsername(), refreshTokenID));
        return tokenResponse;
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        if (!jwtTokenService.verifyRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        String username = jwtTokenService.getUsernameFromToken(refreshToken).orElseThrow();
        UUID refreshTokenID = jwtTokenService.getTokenIdFromToken(refreshToken).orElseThrow();
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(jwtTokenService.generateAccessToken(username, refreshTokenID));
        tokenResponse.setRefreshToken(refreshToken);
        return tokenResponse;
    }
}
