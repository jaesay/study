package com.jaesay.springcache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Cacheable(value = "default", key = "#param")
    public String cachedMethod(String param) {
        return param + "'s value";
    }
}
