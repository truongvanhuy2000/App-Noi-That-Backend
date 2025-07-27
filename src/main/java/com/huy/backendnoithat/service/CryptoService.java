package com.huy.backendnoithat.service;

public interface CryptoService {
    String encrypt(String data);

    String decrypt(String encryptedData);
}
