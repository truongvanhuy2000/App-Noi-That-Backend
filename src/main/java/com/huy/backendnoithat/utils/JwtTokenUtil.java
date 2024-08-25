package com.huy.backendnoithat.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtTokenUtil {
    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1].trim();
    }
}
