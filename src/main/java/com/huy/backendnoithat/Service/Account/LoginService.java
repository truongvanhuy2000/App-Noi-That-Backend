package com.huy.backendnoithat.Service.Account;

import com.huy.backendnoithat.DTO.TokenResponse;

public interface LoginService {
    TokenResponse login(String username, String password);
    TokenResponse refreshToken(String refreshToken);
}
