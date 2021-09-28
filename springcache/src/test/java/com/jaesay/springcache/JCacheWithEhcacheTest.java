package com.jaesay.springcache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import static org.assertj.core.api.Assertions.assertThat;

public class JCacheWithEhcacheTest {

    @Test
    void testJCacheWithEhcache() {
        // CachingProvider is an interface which allows us to create and manage the lifecycle of CacheManagers.
        CachingProvider cachingProvider = Caching.getCachingProvider();

        // CacheManager is one of the most important interfaces of the API. It enables us to establish, configure and close Caches.
        CacheManager cacheManager = cachingProvider.getCacheManager();

        MutableConfiguration<String, String> config = new MutableConfiguration<>();
        Cache<String, String> cache = cacheManager.createCache("simpleCache", config);

        cache.put("key1", "value1");
        cache.put("key2", "value2");

        assertThat(cache.get("key1")).isEqualTo("value1");
        assertThat(cache.get("key2")).isEqualTo("value2");

        cacheManager.close();
    }
}
