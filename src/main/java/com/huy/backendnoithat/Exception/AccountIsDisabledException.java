package com.huy.backendnoithat.Exception;

public class AccountIsDisabledException extends RuntimeException{
    public AccountIsDisabledException(String message) {
        super(message);
    }
    public AccountIsDisabledException(String message, Throwable cause) {
        super(message, cause);
    }
    public AccountIsDisabledException(Throwable cause) {
        super(cause);
    }
    protected AccountIsDisabledException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
