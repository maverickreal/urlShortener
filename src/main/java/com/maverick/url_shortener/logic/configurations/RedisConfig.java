package com.maverick.url_shortener.logic.configurations;

import javax.cache.Cache;
import javax.cache.Caching;
import org.redisson.config.Config;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;

@Configuration
public class RedisConfig {
    @Value("${REDIS_ADDRESS}")
    private String cacheAddress;
    @Value("${CACHE_NAME}")
    private String cacheName;

    @Bean
    ProxyManager<String> getProxyManager() {
        Config config = new Config();
        config.useSingleServer().setAddress(cacheAddress);
        Cache cache = Caching.getCachingProvider().getCacheManager().createCache(cacheName,
                RedissonConfiguration.fromConfig(config));
        return new JCacheProxyManager<>(cache);
    }
}