package com.huy.backendnoithat.Utils;

import com.huy.backendnoithat.DAO.Account.AccountDAO;
import com.huy.backendnoithat.Entity.Account.AccountEntity;
import com.huy.backendnoithat.Exception.AccountExpiredException;
import com.huy.backendnoithat.Exception.AccountIsDisabledException;
import com.huy.backendnoithat.Exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
//    public static final long JWT_TOKEN_VALIDITY = 24L * 60 * 60 * 1000;
    public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 24L;
    public static final long JWT_REFRESH_TOKEN_VALIDITY = 24L * 60 * 60 * 30;
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final AccountDAO accountDAO;
    final PasswordEncoder passwordEncoder;
    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("appnoithat")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
    public String generateRefreshToken(String username, String password) {
        return Jwts.builder()
                .setIssuer("appnoithat")
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public String getIssuerFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuer);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
    public boolean validateToken(String token) {
        return !isTokenExpired(token) && validateAccount(token);
    }
    public boolean validateAccount(String token) {
        String tokenUsername = getUsernameFromToken(token);
        AccountEntity account = accountDAO.findByUsername(tokenUsername);
        if (account == null) {
            throw new InvalidJwtTokenException("Invalid token");
        }
        if (!account.isEnabled() || !account.isActive()) {
            throw new AccountIsDisabledException("Account is disabled");
        }
        if (account.getExpiredDate() == null || account.getExpiredDate().toLocalDate().isBefore(LocalDate.now())) {
            throw new AccountExpiredException("Account is expired");
        }
        return true;
    }
    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1].trim();
    }
}
