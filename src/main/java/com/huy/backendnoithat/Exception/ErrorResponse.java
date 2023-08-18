package com.huy.backendnoithat.Exception;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
