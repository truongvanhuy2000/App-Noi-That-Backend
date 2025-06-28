package com.huy.backendnoithat.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");
    public final String value;
}
