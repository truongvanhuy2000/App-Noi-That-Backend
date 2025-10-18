package com.huy.backendnoithat.usecase.cache;

public interface CacheService {
    void put(String key, Object value, long ttlSec);
    Object get(String key);
}
