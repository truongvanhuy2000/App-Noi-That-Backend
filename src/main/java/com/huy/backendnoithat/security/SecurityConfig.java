package com.huy.backendnoithat.security;

import com.huy.backendnoithat.dao.v0.Account.AccountDAO;
import com.huy.backendnoithat.dao.v1.AccountEntityDAO;
import com.huy.backendnoithat.entity.account.AccountEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig {
    private final AuthenticationHeaderFilter authenticationHeaderFilter;
    private final SecurityCookieFilter securityCookieFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, ExceptionHandlerFilter exceptionHandlerFilter) throws Exception {
        httpSecurity.sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable);
        httpSecurity
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers("/api/accounts/**").hasRole("ADMIN")
                .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/logout").permitAll()
                .requestMatchers("api/refreshToken").permitAll()
                .requestMatchers("/api/register/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/pricingModel").permitAll()
                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers("/api/v1/public/**").permitAll()
                .anyRequest().authenticated());

        httpSecurity.addFilterBefore(authenticationHeaderFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(securityCookieFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(exceptionHandlerFilter, AuthenticationHeaderFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(AccountEntityDAO accountDAO, PasswordEncoder passwordEncoder) {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                if (!(authentication.getPrincipal() instanceof String username)) {
                    log.error("authentication principal must be a instance of String");
                    return null;
                }
                if (!(authentication.getCredentials() instanceof String password)) {
                    log.error("authentication credential must be a instance of String");
                    return null;
                }
                Optional<AccountEntity> accountEntityOptional = accountDAO.findByUsername(username);
                if (accountEntityOptional.isEmpty()) {
                    log.error("Account with username {} not found", username);
                    throw new AuthenticationServiceException("Can't authenticate this account");
                }
                AccountEntity account = accountEntityOptional.get();
                if (!isPasswordMatch(password, account.getPassword())) {
                    log.error("Password is not match");
                    throw new AuthenticationServiceException("Can't authenticate this account");
                }
                List<SimpleGrantedAuthority> authorities = account.getRoleEntity().stream().map(
                    element -> new SimpleGrantedAuthority(element.getRole())).toList();
                return new UsernamePasswordAuthenticationToken(username, null, authorities);
            }

            private boolean isPasswordMatch(String rawPassword, String encodedPassword) {
                return passwordEncoder.matches(rawPassword, encodedPassword);
            }
        };
    }
}
