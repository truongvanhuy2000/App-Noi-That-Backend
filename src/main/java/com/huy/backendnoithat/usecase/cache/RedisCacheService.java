package com.huy.backendnoithat.usecase.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisCacheService implements CacheService {
    private final CacheManager cacheManager;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisCacheService(@Qualifier("RedisCacheManager") CacheManager cacheManager, RedisTemplate<String, Object> redisTemplate) {
        this.cacheManager = cacheManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String key, Object value, long ttlSec) {
        redisTemplate.opsForValue().set(key, value, ttlSec, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String cacheKey) {
        redisTemplate.delete(cacheKey);
    }
}
