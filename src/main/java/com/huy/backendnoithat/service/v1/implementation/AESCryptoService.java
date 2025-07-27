package com.huy.backendnoithat.service.v1.implementation;

import com.huy.backendnoithat.service.CryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

@Primary
@Service("AESCryptoService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AESCryptoService implements CryptoService {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int IV_SIZE = 12; // 96 bits for GCM
    private static final int TAG_LENGTH_BIT = 128;

    private final SecretKey secretKey;

    @Override
    public String encrypt(String data) {
        try {
            // Generate IV
            byte[] iv = new byte[IV_SIZE];
            new SecureRandom().nextBytes(iv);

            // Initialize cipher for encryption
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            // Encrypt data
            byte[] encryptedData = cipher.doFinal(data.getBytes());

            // Combine IV + encrypted data (Base64 encode)
            String ivBase64 = Base64.getEncoder().encodeToString(iv);
            String cipherBase64 = Base64.getEncoder().encodeToString(encryptedData);

            return ivBase64 + ":" + cipherBase64;
        } catch (Exception e) {
            throw new RuntimeException("AES encryption failed", e);
        }
    }

    @Override
    public String decrypt(String encryptedData) {
        try {
            // Split IV and cipher text
            String[] parts = encryptedData.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid encrypted data format");
            }

            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] cipherBytes = Base64.getDecoder().decode(parts[1]);

            // Initialize cipher for decryption
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            // Decrypt data
            byte[] decryptedBytes = cipher.doFinal(cipherBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }
}

