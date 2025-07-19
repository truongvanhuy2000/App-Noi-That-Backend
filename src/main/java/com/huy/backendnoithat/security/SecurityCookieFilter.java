package com.huy.backendnoithat.security;

import com.huy.backendnoithat.service.general.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SecurityCookieFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }
        Optional<Cookie> tokenCookie = Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals(JwtTokenService.TOKEN_COOKIE)).findFirst();
        if (tokenCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = tokenCookie.get().getValue();
        if (jwtTokenService.verifyAccessToken(token)) {
            Long userId = jwtTokenService.getUserIdFromToken(token).orElseThrow();
            List<String> roles = jwtTokenService.getUserRolesFromToken(token);
            List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                userId, token, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
