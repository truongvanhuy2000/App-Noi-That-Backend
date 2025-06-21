package com.huy.backendnoithat.service.compression;

public interface DataCompressService {
    byte[] compressData(byte[] data);
    byte[] decompressData(byte[] compressedData);
}
