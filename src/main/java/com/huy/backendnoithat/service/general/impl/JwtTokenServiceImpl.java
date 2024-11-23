package com.huy.backendnoithat.service.general.impl;

import com.huy.backendnoithat.dao.Account.RefreshTokenDAO;
import com.huy.backendnoithat.entity.Account.AccountEntity;
import com.huy.backendnoithat.entity.Account.RefreshTokenEntity;
import com.huy.backendnoithat.model.dto.AccountManagement.Account;
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {
    public static final long JWT_TOKEN_VALIDITY_MILLIS = TimeUnit.HOURS.toMillis(24);
    public static final long JWT_REFRESH_TOKEN_VALIDITY_MILLIS = TimeUnit.DAYS.toMillis(30);
    private static final String TOKEN_TYPE = "token_type";
    private static final String REFRESH_TOKEN_ID = "refresh_token_id";
    private final Map<String, Date> revokedRefreshTokenMap = new ConcurrentHashMap<>();
    private final RefreshTokenDAO refreshTokenDAO;

    private enum TokenType {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }

    @Override
    public String generateAccessToken(@NonNull String username, @NonNull UUID refreshTokenID) {
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_MILLIS);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("app_noi_that_backend")
                .jwtID(UUID.randomUUID().toString())
                .subject(username)
                .issueTime(new Date())
                .expirationTime(expirationDate)
                .audience("app_noi_that")
                .claim(TOKEN_TYPE, TokenType.ACCESS_TOKEN.toString())
                .claim(REFRESH_TOKEN_ID, refreshTokenID.toString())
                .build();
        return generateSerializedJwtToken(jwtClaimsSet, expirationDate);
    }

    @Override
    public String generateRefreshToken(@NonNull Account account) {
        UUID tokenId = UUID.randomUUID();
        return generateRefreshToken(account, tokenId);
    }

    @Override
    public void revokeRefreshToken(@NonNull String username) {
        List<RefreshTokenEntity> refreshTokenEntityList = refreshTokenDAO.findAllByUsername(username);
        refreshTokenEntityList.forEach(item -> {
            item.setValid(false);
            createNewInvalidationEntry(item.getTokenId());
        });
        refreshTokenDAO.saveAll(refreshTokenEntityList);
    }

    @Override
    public String generateRefreshToken(@NonNull Account account, @NonNull UUID tokenID) {
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY_MILLIS);
        String tokenId = tokenID.toString();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("app_noi_that_backend")
                .jwtID(tokenId)
                .subject(account.getUsername())
                .issueTime(new Date())
                .expirationTime(expirationDate)
                .audience("app_noi_that")
                .claim(TOKEN_TYPE, TokenType.REFRESH_TOKEN.toString())
                .build();
        String token = generateSerializedJwtToken(jwtClaimsSet, expirationDate);

        refreshTokenDAO.save(RefreshTokenEntity.builder()
                .tokenId(tokenId)
                .isValid(true)
                .accountEntity(AccountEntity.builder().id(account.getId()).build())
                .build());
        return token;
    }

    @Override
    public Optional<UUID> getTokenIdFromToken(@NonNull String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return Optional.of(UUID.fromString(signedJWT.getJWTClaimsSet().getJWTID()));
        } catch (ParseException e) {
            log.error("Error", e);
            return Optional.empty();
        }
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
            if (!verifyWithAssociatedRefreshToken(jwtClaimsSet.getStringClaim(REFRESH_TOKEN_ID))) {
                log.info("prematurely invalidated");
                return false;
            }
            return verifyJWT(signedJWT);
        } catch (ParseException | JOSEException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<String> getUsernameFromToken(@NonNull String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return Optional.of(signedJWT.getJWTClaimsSet().getSubject());
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
            log.info("expired access token");
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

    /**
     * @param tokenID This expirationDate mean that the amount of time this token will exist inside this map,
     *                so that during this time, any access token that associate with this token will be prematurely invalidated
     */
    private void createNewInvalidationEntry(String tokenID) {
        Date expirationDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_MILLIS);
        revokedRefreshTokenMap.put(tokenID, expirationDate);
    }

    /**
     * @param tokenID We will use this to check if the access token is prematurely invalidated
     * @return
     */
    private boolean verifyWithAssociatedRefreshToken(String tokenID) {
        // If this map doesn't contain this tokenID then maybe the access token is still valid
        if (!revokedRefreshTokenMap.containsKey(tokenID)) {
            return true;
        }
        removeIfExpiredRefreshTokenEntry(tokenID);
        return false;
    }

    private void removeIfExpiredRefreshTokenEntry(String tokenID) {
        if (revokedRefreshTokenMap.containsKey(tokenID)) {
            // TODO: Implement better strategy to remove the token from map
            Date expirationDate = revokedRefreshTokenMap.get(tokenID);
            if (expirationDate.before(new Date())) {
                revokedRefreshTokenMap.remove(tokenID);
            }
        }
    }
}
