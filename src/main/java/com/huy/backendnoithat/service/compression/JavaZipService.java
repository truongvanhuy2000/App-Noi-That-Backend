package com.huy.backendnoithat.service.compression;

import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
public class JavaZipService implements DataCompressService {
    @Override
    public byte[] compressData(byte[] data) {
        if (data == null || data.length == 0) return new byte[0];

        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream)
        ) {
            gzipStream.write(data);
            gzipStream.finish();
            return byteStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to compress data", e);
        }
    }

    @Override
    public byte[] decompressData(byte[] compressedData) {
        if (compressedData == null || compressedData.length == 0) return new byte[0];

        try (ByteArrayInputStream byteInputStream = new ByteArrayInputStream(compressedData);
             GZIPInputStream gzipInputStream = new GZIPInputStream(byteInputStream);
             ByteArrayOutputStream outStream = new ByteArrayOutputStream()
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, len);
            }
            return outStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to decompress data", e);
        }
    }
}
