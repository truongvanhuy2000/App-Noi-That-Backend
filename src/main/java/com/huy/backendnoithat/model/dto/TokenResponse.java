package com.huy.backendnoithat.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
    private String accessToken;
    private Date accessTokenExpiration;
    private String refreshToken;
    private Date refreshTokenExpiration;
}
