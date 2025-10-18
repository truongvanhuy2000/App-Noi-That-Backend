package com.huy.backendnoithat.usecase.v1.implementation;

import com.huy.backendnoithat.usecase.crypto.AESCryptoService;
import org.junit.jupiter.api.Test;

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