package com.huy.backendnoithat.service.account.impl;

import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.TokenResponse;
import com.huy.backendnoithat.exception.AccountExpiredException;
import com.huy.backendnoithat.exception.AccountIsDisabledException;
import com.huy.backendnoithat.service.account.AccountService;
import com.huy.backendnoithat.service.account.LoginService;
import com.huy.backendnoithat.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
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
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(jwtTokenUtil.generateAccessToken(username));
        tokenResponse.setRefreshToken(jwtTokenUtil.generateRefreshToken(username, password));
        return tokenResponse;
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        if (!jwtTokenUtil.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(jwtTokenUtil.generateAccessToken(username));
        tokenResponse.setRefreshToken(refreshToken);
        return tokenResponse;
    }
}
