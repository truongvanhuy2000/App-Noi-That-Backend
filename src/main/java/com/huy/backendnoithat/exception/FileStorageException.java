package com.huy.backendnoithat.exception;

import com.huy.backendnoithat.exception.errorCode.FileStorageErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class FileStorageException extends RuntimeException {
    private final FileStorageErrorCode fileStorageErrorCode;
    public FileStorageException(String message, FileStorageErrorCode fileStorageErrorCode) {
        super(message);
        this.fileStorageErrorCode = fileStorageErrorCode;
    }

    public FileStorageException(String message, Throwable cause, FileStorageErrorCode fileStorageErrorCode) {
        super(message, cause);
        this.fileStorageErrorCode = fileStorageErrorCode;
    }

    public FileStorageException(Throwable cause, FileStorageErrorCode fileStorageErrorCode) {
        super(cause);
        this.fileStorageErrorCode = fileStorageErrorCode;
    }

    public FileStorageException(FileStorageErrorCode fileStorageErrorCode) {
        super();
        this.fileStorageErrorCode = fileStorageErrorCode;
    }
}
