package com.huy.backendnoithat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.backendnoithat.exception.AccountExpiredException;
import com.huy.backendnoithat.exception.AccountIsDisabledException;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.dto.TokenResponse;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.huy.backendnoithat.service.v0.account.LoginService;
import com.huy.backendnoithat.service.v1.AccountManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Slf4j
@Service("AuthenticationService")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthenticationService implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final AccountManagementService accountManagementService;
    private final AccountRestrictionService accountRestrictionService;
    private final CryptoService cryptoService;

    @Override
    public TokenResponse login(String username, String password) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        Account account = accountManagementService.findByUsername(username);
        if (account == null) {
            throw new AuthenticationException();
        }
        if (accountRestrictionService.isAccountExpired(account.getId())) {
            log.error("login - Account with username {} is expired", username);
            throw new AccountExpiredException("Account is expired");
        }
        if (!account.getEnabled() || !account.getActive()) {
            log.error("login - Account with username {} is disabled", username);
            throw new AccountIsDisabledException("Account is disabled");
        }
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
    public TokenResponse refreshToken(String refreshToken) throws AuthenticationException {
        if (!jwtTokenService.verifyRefreshToken(refreshToken)) {
            log.error("Invalid refresh token: {}", refreshToken);
            throw new AuthenticationException("Invalid refresh token");
        }
        String username = jwtTokenService.getSubjectFromToken(refreshToken).orElseThrow();
        Account account = accountManagementService.findByUsername(username);
        if (accountRestrictionService.isAccountExpired(account.getId())) {
            log.error("refreshToken - Account with username {} is expired", username);
            throw new AccountExpiredException("Account is expired");
        }
        if (!account.getEnabled() || !account.getActive()) {
            log.error("refreshToken - Account with username {} is disabled", username);
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

    @Override
    public TokenResponse parseDigitalSignature(String digitalSignature) throws AuthenticationException {
        String decryptedData = cryptoService.decrypt(digitalSignature);
        return refreshToken(decryptedData);
    }
}
