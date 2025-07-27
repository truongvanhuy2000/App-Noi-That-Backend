package com.huy.backendnoithat.service.v1.implementation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AESCryptoServiceTest {
    AESCryptoService aesCryptoService = new AESCryptoService(aesKey());

    public SecretKey aesKey() {
        String ALGORITHM = "AES";
        byte[] keyBytes;
        String base64Key = "OPxDyeUEHrQwWHiXnHpWcDjsM137f8VobkT8o7yFoEg=";
        keyBytes = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    @Test
    void encrypt() {
        String original = "hello";
        String encrypted = aesCryptoService.encrypt(original);
        String decrypted = aesCryptoService.decrypt(encrypted);
        assertEquals(original, decrypted);
    }

    @Test
    void decrypt() {
    }
}