package com.huy.backendnoithat.service;

import com.huy.backendnoithat.model.enums.DataOperation;
import com.huy.backendnoithat.service.compression.DataCompressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DataProcessingService {
    // This service can be used to handle data processing tasks such as validation, transformation, etc.
    // Currently, it is empty but can be expanded in the future as needed.
    private final DataCompressService dataCompressService;

    public byte[] processData(byte[] data, DataOperation dataOperation) {
        try {
            return switch (dataOperation) {
                case COMPRESS -> dataCompressService.compressData(data);
                case DECOMPRESS -> dataCompressService.decompressData(data);
            };
        } catch (Exception e) {
            log.error("Error processing data : {}, return as it is", e.getMessage(), e);
            return data;
        }
    }
}
