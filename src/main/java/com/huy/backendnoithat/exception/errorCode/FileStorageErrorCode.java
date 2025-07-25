package com.huy.backendnoithat.exception.errorCode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FileStorageErrorCode {
    FILE_NOT_FOUND("FS1", "File not found"),
    CANT_TRANSFER("FS2", "Can't transfer"),
    CANT_ACCESS_FILE("FS3", "Can't access this file"),
    FILE_LIMIT_REACHED("FS4", "File limit reached");
    public final String errorCode;
    public final String description;
}
