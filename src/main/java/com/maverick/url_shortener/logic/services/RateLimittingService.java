package com.maverick.url_shortener.logic.services;

import java.time.Duration;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BandwidthBuilder;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import reactor.core.publisher.Mono;

@Service
public class RateLimittingService {
    @Autowired
    private ProxyManager buckets;
    @Value("${tokenBucketCapacity}")
    private Long tokenBucketCapacity;
    @Value("${tokenBucketPeriod}")
    private int tokenBucketPeriod;

    public Mono<Boolean> allowRequest(String key) {
        Bandwidth limit = BandwidthBuilder.builder()
                .capacity(tokenBucketCapacity)
                .refillIntervally(tokenBucketCapacity, Duration.ofMinutes(tokenBucketPeriod))
                .build();
        Supplier<BucketConfiguration> bucketConfigSup = () -> BucketConfiguration.builder().addLimit(limit).build();
        return Mono.just(buckets.builder().build(key, bucketConfigSup).tryConsume(1));
    }
}