package com.huy.backendnoithat.service.v0.account.impl;

import com.huy.backendnoithat.exception.AccountExpiredException;
import com.huy.backendnoithat.exception.AccountIsDisabledException;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.TokenResponse;
import com.huy.backendnoithat.service.v0.account.AccountService;
import com.huy.backendnoithat.service.v0.account.LoginService;
import com.huy.backendnoithat.service.general.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final AccountService accountService;

    @Override
    public TokenResponse login(String username, String password) throws AuthenticationException {
        Account account = accountService.findByUsername(username);
        if (account == null) {
            throw new AuthenticationException();
        }
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
        String refreshToken = jwtTokenService.generateRefreshToken(account.getUsername());
        String accessToken = jwtTokenService.generateAccessToken((long) account.getId(), account.getUsername(), account.getRoles());
        return TokenResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .accessTokenExpiration(jwtTokenService.getExpirationDateFromToken(accessToken).orElseThrow())
                .refreshTokenExpiration(jwtTokenService.getExpirationDateFromToken(refreshToken).orElseThrow())
                .build();
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        if (!jwtTokenService.verifyRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        String username = jwtTokenService.getSubjectFromToken(refreshToken).orElseThrow();
        Account account = accountService.findByUsername(username);
        if (account.getExpiredDate().isBefore(LocalDate.now())) {
            throw new AccountExpiredException("Account is expired");
        }
        if (!account.isEnabled() || !account.isActive()) {
            throw new AccountIsDisabledException("Account is disabled");
        }
        String accessToken = jwtTokenService.generateAccessToken((long) account.getId(), account.getUsername(), account.getRoles());
        return TokenResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .accessTokenExpiration(jwtTokenService.getExpirationDateFromToken(accessToken).orElseThrow())
                .refreshTokenExpiration(jwtTokenService.getExpirationDateFromToken(refreshToken).orElseThrow())
                .build();
    }
}
