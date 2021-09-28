package com.jaesay.springcache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CacheServiceTest {

    @Autowired
    CacheManager cacheManager;

    @Autowired
    CacheService cacheService;

    @Test
    public void testCache() {
        String cachedData = cacheService.cachedMethod("key");
        Cache cache = cacheManager.getCache("default");
        assertThat(cache.get("key").get()).isEqualTo(cachedData);
    }
}