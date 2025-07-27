package com.huy.backendnoithat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class KeyLoaderConfig {
    @Bean
    public KeyPair keyLoader() {
        ClassPathResource privateKeyResource = new ClassPathResource("/key/private.pem");
        ClassPathResource publicKeyResource = new ClassPathResource("/key/public.pem");

        try (InputStream privateKeyInputStream = privateKeyResource.getInputStream();
             InputStream publicKeyInputStream = publicKeyResource.getInputStream()) {
            String privatePem = new String(privateKeyInputStream.readAllBytes());
            String publicPem = new String(publicKeyInputStream.readAllBytes());

            privatePem = privatePem.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
            publicPem = publicPem.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

            byte[] privateKeyBytes = Base64.getDecoder().decode(privatePem);
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicPem);

            // Create KeyFactory
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            // Generate Private and Public Key objects
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

            return new KeyPair(publicKey, privateKey);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read key files", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RSA algorithm not found", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid key specification", e);
        }
    }

    @Bean
    public SecretKey aesKey() {
        String ALGORITHM = "AES";
        byte[] keyBytes;
        try (var is = new ClassPathResource("/key/aes.key").getInputStream()) {
            String base64Key = new String(is.readAllBytes()).trim();
            keyBytes = Base64.getDecoder().decode(base64Key);
            return new SecretKeySpec(keyBytes, ALGORITHM);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
