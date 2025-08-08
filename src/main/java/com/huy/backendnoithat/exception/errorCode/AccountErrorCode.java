package com.huy.backendnoithat.exception.errorCode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccountErrorCode {
    ACCOUNT_NOT_FOUND("AC1", "Account not found"),
    ACCOUNT_EXPIRED("AC2", "Account expired"),
    INVALID_CREDENTIALS("AC3", "Invalid credentials"),
    ACCOUNT_LOCKED("AC4", "Account locked"),
    ACCOUNT_ALREADY_EXISTS("AC5", "Account already exists");

    public final String errorCode;
    public final String description;
}
