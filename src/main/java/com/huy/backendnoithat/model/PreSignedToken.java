package com.huy.backendnoithat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PreSignedToken {
    private String token;
    private String resourceId;
}
