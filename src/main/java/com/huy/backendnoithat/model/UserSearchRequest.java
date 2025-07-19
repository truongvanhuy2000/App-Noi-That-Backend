package com.huy.backendnoithat.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchRequest {
    private String username;
    private String email;
    private Boolean activationStatus;
    private Boolean enableStatus;
}
