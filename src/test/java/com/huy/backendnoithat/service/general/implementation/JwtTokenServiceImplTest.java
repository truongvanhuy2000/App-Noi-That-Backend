//package com.huy.backendnoithat.service.general.implementation;
//
//import com.huy.backendnoithat.dao.Account.RefreshTokenDAO;
//import com.huy.backendnoithat.entity.Account.RefreshTokenEntity;
//import com.huy.backendnoithat.model.dto.AccountManagement.Account;
//import com.nimbusds.jose.*;
//import com.nimbusds.jose.crypto.ECDSASigner;
//import com.nimbusds.jose.crypto.RSASSASigner;
//import com.nimbusds.jose.jwk.Curve;
//import com.nimbusds.jose.jwk.ECKey;
//import com.nimbusds.jose.jwk.KeyUse;
//import com.nimbusds.jose.jwk.RSAKey;
//import com.nimbusds.jose.jwk.gen.ECKeyGenerator;
//import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.PlainJWT;
//import com.nimbusds.jwt.SignedJWT;
//import org.jeasy.random.EasyRandom;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.lang.reflect.Field;
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class JwtTokenServiceImplTest {
//    @Mock
//    RefreshTokenDAO refreshTokenDAO;
//    @InjectMocks
//    JwtTokenServiceImpl jwtTokenService;
//    @Test
//    void generateAccessToken() {
//        String username = "test";
//        UUID refreshTokenUUID = UUID.randomUUID();
//        jwtTokenService.generateAccessToken(username, refreshTokenUUID);
//    }
//
//    @Test
//    void generateRefreshToken() {
//        ArgumentCaptor<RefreshTokenEntity> refreshTokenEntityAC = ArgumentCaptor.forClass(RefreshTokenEntity.class);
//        Account account = Account.builder()
//                .id(0)
//                .username("test")
//                .build();
//        UUID refreshTokenUUID = UUID.randomUUID();
//        jwtTokenService.generateRefreshToken(account, refreshTokenUUID);
//
//        verify(refreshTokenDAO).save(refreshTokenEntityAC.capture());
//
//        RefreshTokenEntity refreshTokenEntity = refreshTokenEntityAC.getValue();
//        assertTrue(refreshTokenEntity.isValid());
//        assertEquals(refreshTokenEntity.getTokenId(), refreshTokenUUID.toString());
//        assertEquals(refreshTokenEntity.getAccountEntity().getId(), account.getId());
//    }
//
//    @Test
//    void generateRefreshToken_nullID() {
//        Account account = Account.builder()
//                .id(0)
//                .username("test")
//                .build();
//        assertThrows(RuntimeException.class, () -> jwtTokenService.generateRefreshToken(account, null));
//    }
//
//    @Test
//    void generateRefreshToken_nullAccount() {
//        UUID refreshTokenUUID = UUID.randomUUID();
//        assertThrows(RuntimeException.class, () -> jwtTokenService.generateRefreshToken(null, refreshTokenUUID));
//    }
//
//    @Test
//    void revokeRefreshToken() {
//        String username = "test";
//        EasyRandom generator = new EasyRandom();
//        ArgumentCaptor<List<RefreshTokenEntity>> refreshTokenEntityAC = ArgumentCaptor.forClass(List.class);
//        List<RefreshTokenEntity> refreshTokenEntityList = generator.objects(RefreshTokenEntity.class, 10).toList();
//        given(refreshTokenDAO.findAllByUsername(username)).willReturn(refreshTokenEntityList);
//        jwtTokenService.revokeRefreshToken(username);
//        verify(refreshTokenDAO).saveAllAndFlush(refreshTokenEntityAC.capture());
//        List<RefreshTokenEntity> list = refreshTokenEntityAC.getValue();
//        assertTrue(() -> list.stream().noneMatch(RefreshTokenEntity::isValid));
//    }
//
//
//    @Test
//    void getTokenIdFromToken() {
//        String username = "test";
//        UUID refreshTokenUUID = UUID.randomUUID();
//        String token = jwtTokenService.generateAccessToken(username, refreshTokenUUID);
//        Optional<UUID> optional = jwtTokenService.getTokenIdFromToken(token);
//        assertTrue(optional.isPresent());
//    }
//
//    @Test
//    void verifyAccessToken() {
//        String username = "test";
//        UUID refreshTokenUUID = UUID.randomUUID();
//        String token = jwtTokenService.generateAccessToken(username, refreshTokenUUID);
//        assertTrue(jwtTokenService.verifyAccessToken(token));
//    }
//
//    @Test
//    void verifyAccessToken_prematureInvalidated() throws NoSuchFieldException, IllegalAccessException {
//        String username = "test";
//        UUID refreshTokenUUID = UUID.randomUUID();
//        Map<String, Date> revokedRefreshTokenMap = getPrivateRevokedRefreshTokenMap(jwtTokenService);
//        revokedRefreshTokenMap.put(refreshTokenUUID.toString(), new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(10)));
//        String token = jwtTokenService.generateAccessToken(username, refreshTokenUUID);
//        assertFalse(jwtTokenService.verifyAccessToken(token));
//        assertFalse(revokedRefreshTokenMap.containsKey(refreshTokenUUID.toString()));
//    }
//
//    @Test
//    void verifyAccessToken_badToken() {
//        String token = "test";
//        assertFalse(jwtTokenService.verifyAccessToken(token));
//    }
//
//    @Test
//    void verifyAccessToken_wrongTokenType() {
//        Account account = Account.builder()
//                .id(0)
//                .username("test")
//                .build();
//        UUID refreshTokenUUID = UUID.randomUUID();
//        String token = jwtTokenService.generateRefreshToken(account, refreshTokenUUID);
//        assertFalse(jwtTokenService.verifyAccessToken(token));
//    }
//
//    @Test
//    void verifyAccessToken_expiredToken() throws JOSEException {
//        String username = "test";
//        UUID refreshTokenUUID = UUID.randomUUID();
//        String token = createExpiredJWT(username, refreshTokenUUID);
//        assertFalse(jwtTokenService.verifyAccessToken(token));
//    }
//
//    @Test
//    void verifyAccessToken_wrongKeyType() throws JOSEException {
//        String username = "test";
//        UUID refreshTokenUUID = UUID.randomUUID();
//        String token = createRSAKeyType(username, refreshTokenUUID);
//        assertFalse(jwtTokenService.verifyAccessToken(token));
//    }
//
//    @Test
//    void verifyAccessToken_wrongSignature() throws JOSEException {
//        String username = "test";
//        UUID refreshTokenUUID = UUID.randomUUID();
//        String token = createWithBadSignature(username, refreshTokenUUID);
//        assertFalse(jwtTokenService.verifyAccessToken(token));
//    }
//
//    @Test
//    void verifyAccessToken_tokenNotSigned() {
//        String username = "test";
//        UUID refreshTokenUUID = UUID.randomUUID();
//        String token = createWithoutSigning(username, refreshTokenUUID);
//        assertFalse(jwtTokenService.verifyAccessToken(token));
//    }
//
//    @Test
//    void getUsernameFromToken() {
//        String username = "test";
//        UUID refreshTokenUUID = UUID.randomUUID();
//        String token = jwtTokenService.generateAccessToken(username, refreshTokenUUID);
//        Optional<String> optional = jwtTokenService.getUsernameFromToken(token);
//        assertTrue(optional.isPresent());
//        assertEquals(optional.get(), username);
//    }
//
//    @Test
//    void verifyRefreshToken() {
//        Account account = Account.builder()
//                .id(0)
//                .username("test")
//                .build();
//        UUID refreshTokenUUID = UUID.randomUUID();
//        String token = jwtTokenService.generateRefreshToken(account, refreshTokenUUID);
//
//        assertTrue(jwtTokenService.verifyRefreshToken(token));
//    }
//
//    @Test
//    void verifyRefreshToken_wrongType() {
//        Account account = Account.builder()
//                .id(0)
//                .username("test")
//                .build();
//        UUID refreshTokenUUID = UUID.randomUUID();
//        String token = jwtTokenService.generateAccessToken(account.getUsername(), refreshTokenUUID);
//
//        assertFalse(jwtTokenService.verifyRefreshToken(token));
//    }
//
//    private String createExpiredJWT(String username, UUID tokenID) throws JOSEException {
//        Date expiration = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(10));
//        ECKey ecKey = new ECKeyGenerator(Curve.P_256)
//                .keyID(UUID.randomUUID().toString())
//                .expirationTime(expiration)
//                .issueTime(new Date())
//                .algorithm(JWSAlgorithm.ES256)
//                .keyUse(KeyUse.SIGNATURE)
//                .generate();
//
//        JWSSigner jwsSigner = new ECDSASigner(ecKey.toECPrivateKey());
//        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.ES256)
//                .type(JOSEObjectType.JWT)
//                .jwk(ecKey.toPublicJWK())
//                .build();
//
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .issuer("app_noi_that_backend")
//                .jwtID(UUID.randomUUID().toString())
//                .subject(username)
//                .issueTime(new Date())
//                .expirationTime(expiration)
//                .audience("app_noi_that")
//                .claim("token_type", "ACCESS_TOKEN")
//                .claim("refresh_token_id", tokenID)
//                .build();
//
//        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
//        signedJWT.sign(jwsSigner);
//        return signedJWT.serialize();
//    }
//
//    private String createRSAKeyType(String username, UUID tokenID) throws JOSEException {
//        Date expiration = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10));
//        RSAKey rsaKey = new RSAKeyGenerator(2048)
//                .keyID(UUID.randomUUID().toString())
//                .expirationTime(expiration)
//                .issueTime(new Date())
//                .algorithm(JWSAlgorithm.RS256)
//                .keyUse(KeyUse.SIGNATURE)
//                .generate();
//
//        JWSSigner jwsSigner = new RSASSASigner(rsaKey.toPrivateKey());
//        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256)
//                .type(JOSEObjectType.JWT)
//                .jwk(rsaKey.toPublicJWK())
//                .build();
//
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .issuer("app_noi_that_backend")
//                .jwtID(UUID.randomUUID().toString())
//                .subject(username)
//                .issueTime(new Date())
//                .expirationTime(expiration)
//                .audience("app_noi_that")
//                .claim("token_type", "ACCESS_TOKEN")
//                .claim("refresh_token_id", tokenID)
//                .build();
//
//        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
//        signedJWT.sign(jwsSigner);
//        return signedJWT.serialize();
//    }
//
//    private String createWithBadSignature(String username, UUID tokenID) throws JOSEException {
//        Date expiration = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10));
//        ECKey ecKey = new ECKeyGenerator(Curve.P_256)
//                .keyID(UUID.randomUUID().toString())
//                .expirationTime(expiration)
//                .issueTime(new Date())
//                .algorithm(JWSAlgorithm.ES256)
//                .keyUse(KeyUse.SIGNATURE)
//                .generate();
//
//        ECKey ecKey2 = new ECKeyGenerator(Curve.P_256)
//                .keyID(UUID.randomUUID().toString())
//                .expirationTime(expiration)
//                .issueTime(new Date())
//                .algorithm(JWSAlgorithm.ES256)
//                .keyUse(KeyUse.SIGNATURE)
//                .generate();
//
//        JWSSigner jwsSigner = new ECDSASigner(ecKey.toECPrivateKey());
//        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.ES256)
//                .type(JOSEObjectType.JWT)
//                .jwk(ecKey2.toPublicJWK())
//                .build();
//
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .issuer("app_noi_that_backend")
//                .jwtID(UUID.randomUUID().toString())
//                .subject(username)
//                .issueTime(new Date())
//                .expirationTime(expiration)
//                .audience("app_noi_that")
//                .claim("token_type", "ACCESS_TOKEN")
//                .claim("refresh_token_id", tokenID)
//                .build();
//
//        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
//        signedJWT.sign(jwsSigner);
//        return signedJWT.serialize();
//    }
//
//    private String createWithoutSigning(String username, UUID tokenID) {
//        Date expiration = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(10));
//
//        PlainHeader plainHeader = new PlainHeader.Builder()
//                .type(JOSEObjectType.JWT)
//                .build();
//
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .issuer("app_noi_that_backend")
//                .jwtID(UUID.randomUUID().toString())
//                .subject(username)
//                .issueTime(new Date())
//                .expirationTime(expiration)
//                .audience("app_noi_that")
//                .claim("token_type", "ACCESS_TOKEN")
//                .claim("refresh_token_id", tokenID)
//                .build();
//
//        PlainJWT plainJWT = new PlainJWT(plainHeader, jwtClaimsSet);
//        return plainJWT.serialize();
//    }
//
//    private Map<String, Date> getPrivateRevokedRefreshTokenMap(Object object) throws NoSuchFieldException, IllegalAccessException {
//        Field field = JwtTokenServiceImpl.class.getDeclaredField("revokedRefreshTokenMap");
//        field.setAccessible(true);
//        return (Map<String, Date>) field.get(object);
//    }
//}