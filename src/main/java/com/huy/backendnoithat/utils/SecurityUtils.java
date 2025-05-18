package com.huy.backendnoithat.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtils {
    public static long getUserFromContext() {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Long userID)) {
            log.error("User ID not found in security context");
            throw new RuntimeException("User not found");
        }
        return userID;
    }

    public static int getUserFromContext(SecurityContext securityContext) {
        if (!(securityContext.getAuthentication().getPrincipal() instanceof Number userID)) {
            log.error("User ID not found in security context");
            throw new RuntimeException("User not found");
        }
        return userID.intValue();
    }

    public static String getTokenFromContext(SecurityContext securityContext) {
        if (!(securityContext.getAuthentication().getCredentials() instanceof String token)) {
            log.error("Token not found in security context");
            throw new RuntimeException("User not found");
        }
        return token;
    }
}
