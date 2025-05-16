package com.huy.backendnoithat.security;

import com.huy.backendnoithat.exception.AccountExpiredException;
import com.huy.backendnoithat.exception.AccountIsDisabledException;
import com.huy.backendnoithat.service.general.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthenticationHeaderFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException, AccountIsDisabledException, AccountExpiredException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.split(" ")[1].trim();
            if (jwtTokenService.verifyAccessToken(token)) {
                Long userId = jwtTokenService.getUserIdFromToken(token).orElseThrow();
                List<String> roles = jwtTokenService.getUserRolesFromToken(token);
                List<SimpleGrantedAuthority> authorityList = roles.stream().map(SimpleGrantedAuthority::new).toList();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userId, token, authorityList);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
