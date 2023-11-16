package com.huy.backendnoithat.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity()
public class AppNoiThatSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    @Autowired
    public AppNoiThatSecurityConfig(JwtTokenFilter jwtTokenFilter, ExceptionHandlerFilter exceptionHandlerFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
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
                        .anyRequest().authenticated())
                        .logout(logout -> logout.deleteCookies("JSESSIONID"));
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(exceptionHandlerFilter, JwtTokenFilter.class);
        return httpSecurity.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
