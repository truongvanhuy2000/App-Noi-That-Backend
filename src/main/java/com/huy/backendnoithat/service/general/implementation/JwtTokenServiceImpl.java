package com.huy.backendnoithat.service.general.implementation;

import com.huy.backendnoithat.model.dto.AccountManagement.Account;
import com.huy.backendnoithat.model.enums.UserRole;
import com.huy.backendnoithat.service.general.JwtTokenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.Curve;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.Cookie;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {
    private static final String ISSUER = "app-nt-be";
    private static final String TOKEN_TYPE = "token_type";
    private static final String ROLE_CLAIM = "role";
    private static final String ACCOUNT_CLAIM = "account";
    private static final String USER_ID = "user_id";
    private static final String REFRESH_TOKEN_ID = "refresh_token_id";

    private enum TokenType {
        ACCESS_TOKEN,
        REFRESH_TOKEN,
        GENERIC_TOKEN
    }

    @Override
    public String generateToken(Map<String, Object> claims, @NonNull Date expirationDate) {
        if (claims == null || claims.isEmpty()) {
            throw new IllegalArgumentException("Claims must not be null or empty");
        }
        var jwtClaimsSetBuilder = new JWTClaimsSet.Builder()
            .issuer(ISSUER)
            .jwtID(UUID.randomUUID().toString())
            .issueTime(new Date())
            .claim(TOKEN_TYPE, TokenType.GENERIC_TOKEN.toString())
            .expirationTime(expirationDate);
        claims.forEach(jwtClaimsSetBuilder::claim);
        return generateSerializedJwtToken(jwtClaimsSetBuilder.build(), expirationDate);
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return verifyJWT(signedJWT);
        } catch (ParseException | JOSEException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Map<String, Object> getClaimsFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            return jwtClaimsSet.getClaims();
        } catch (ParseException e) {
            log.error(e.getMessage());
            throw new RuntimeException();
        }
    }


    @Override
    public String generateAccessToken(Long userId, String username, List<String> roles) {
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_MILLIS);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
            .issuer(ISSUER)
            .jwtID(UUID.randomUUID().toString())
            .subject(username)
            .issueTime(new Date())
            .expirationTime(expirationDate)
            .claim(TOKEN_TYPE, TokenType.ACCESS_TOKEN.toString())
            .claim(USER_ID, userId)
            .claim(ROLE_CLAIM, roles)
            .build();
        return generateSerializedJwtToken(jwtClaimsSet, expirationDate);
    }

    @Override
    public Optional<String> getUsernameFromToken(String token) {
        return getSubjectFromToken(token);
    }

    @Override
    public String generateRefreshToken(@NonNull String username) {
        UUID tokenId = UUID.randomUUID();
        return generateRefreshToken(username, tokenId);
    }

    private String generateRefreshToken(@NonNull String username, @NonNull UUID tokenID) {
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY_MILLIS);
        String tokenId = tokenID.toString();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
            .issuer(ISSUER)
            .jwtID(tokenId)
            .subject(username)
            .issueTime(new Date())
            .expirationTime(expirationDate)
            .claim(TOKEN_TYPE, TokenType.REFRESH_TOKEN.toString())
            .build();
        return generateSerializedJwtToken(jwtClaimsSet, expirationDate);
    }

    @Override
    public Optional<Date> getExpirationDateFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return Optional.of(signedJWT.getJWTClaimsSet().getExpirationTime());
        } catch (ParseException e) {
            log.error("Error", e);
            return Optional.empty();
        }
    }

    @Override
    public boolean verifyAccessToken(@NonNull String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            if (!jwtClaimsSet.getStringClaim(TOKEN_TYPE).equals(TokenType.ACCESS_TOKEN.toString())) {
                log.info("not access token");
                return false;
            }
            return verifyJWT(signedJWT);
        } catch (ParseException | JOSEException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<String> getSubjectFromToken(@NonNull String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return Optional.of(signedJWT.getJWTClaimsSet().getSubject());
        } catch (ParseException e) {
            log.error("Error", e);
            return Optional.empty();
        }
    }

    @Override
    public List<String> getUserRolesFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            List<String> roles = signedJWT.getJWTClaimsSet().getStringListClaim(ROLE_CLAIM);
            if (roles.isEmpty()) {
                return null;
            }
            return roles.stream().toList();
        } catch (ParseException e) {
            log.error("Error", e);
            return null;
        }
    }

    @Override
    public Optional<Long> getUserIdFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return Optional.of(signedJWT.getJWTClaimsSet().getLongClaim(USER_ID));
        } catch (ParseException e) {
            log.error("Error", e);
            return Optional.empty();
        }
    }

    @Override
    public boolean verifyRefreshToken(@NonNull String refreshToken) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(refreshToken);
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            if (!jwtClaimsSet.getStringClaim(TOKEN_TYPE).equals(TokenType.REFRESH_TOKEN.toString())) {
                log.info("not refresh token");
                return false;
            }
            return verifyJWT(signedJWT);
        } catch (ParseException | JOSEException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private String generateSerializedJwtToken(JWTClaimsSet jwtClaimsSet, Date expirationDate) {
        try {
            ECKey ecKey = generateSignature(expirationDate);
            JWSSigner jwsSigner = new ECDSASigner(ecKey.toECPrivateKey());
            JWSHeader jwsHeader = createJWSHeader(ecKey);
            SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private boolean verifyJWT(SignedJWT signedJWT) throws JOSEException {
        if (isTokenExpired(signedJWT)) {
            log.info("Expired access token");
            return false;
        }
        JWK publicJWK = signedJWT.getHeader().getJWK();
        if (!(publicJWK instanceof ECKey ecKey)) {
            log.error("Broken jwk");
            return false;
        }
        JWSVerifier jwsVerifier = new ECDSAVerifier(ecKey);
        return signedJWT.verify(jwsVerifier);
    }

    private JWSHeader createJWSHeader(JWK jwk) {
        return new JWSHeader.Builder(JWSAlgorithm.ES256)
            .type(JOSEObjectType.JWT)
            .jwk(jwk.toPublicJWK())
            .build();
    }

    private ECKey generateSignature(@NonNull Date signatureExpiration) throws JOSEException {
        return new ECKeyGenerator(Curve.P_256)
            .keyID(UUID.randomUUID().toString())
            .expirationTime(signatureExpiration)
            .issueTime(new Date())
            .algorithm(JWSAlgorithm.ES256)
            .keyUse(KeyUse.SIGNATURE)
            .generate();
    }

    private boolean isTokenExpired(JWT token) {
        try {
            Date expiration = token.getJWTClaimsSet().getExpirationTime();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
