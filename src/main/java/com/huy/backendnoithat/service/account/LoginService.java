package com.huy.backendnoithat.service.account;

import com.huy.backendnoithat.model.dto.TokenResponse;

public interface LoginService {
    TokenResponse login(String username, String password);

    TokenResponse refreshToken(String refreshToken);
}
