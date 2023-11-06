package com.huy.backendnoithat.Exception;

public class InvalidJwtTokenException extends RuntimeException  {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
