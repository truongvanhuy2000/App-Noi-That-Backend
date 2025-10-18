package com.huy.backendnoithat.usecase.crypto;

public interface CryptoService {
    String encrypt(String data);

    String decrypt(String encryptedData);
}
