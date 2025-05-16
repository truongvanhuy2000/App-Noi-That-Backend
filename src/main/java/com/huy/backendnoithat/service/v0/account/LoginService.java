package com.huy.backendnoithat.service.v0.account;

import com.huy.backendnoithat.model.dto.TokenResponse;

import javax.naming.AuthenticationException;

public interface LoginService {
    TokenResponse login(String username, String password) throws AuthenticationException;

    TokenResponse refreshToken(String refreshToken);
}
