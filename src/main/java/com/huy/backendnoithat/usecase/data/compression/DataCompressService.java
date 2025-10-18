package com.huy.backendnoithat.usecase.data.compression;

public interface DataCompressService {
    byte[] compressData(byte[] data);
    byte[] decompressData(byte[] compressedData);
}
