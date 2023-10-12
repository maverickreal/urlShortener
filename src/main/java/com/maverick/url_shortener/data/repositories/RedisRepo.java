package com.maverick.url_shortener.data.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import com.maverick.url_shortener.logic.services.Link;
import reactor.core.publisher.Mono;

@Repository
public class RedisRepo implements ShortenerRepo {
    @Autowired
    private ReactiveRedisOperations<String, String> ops;

    @Override
    public Mono<Link> save(Link link) {
        return ops.opsForValue().set(link.key(), link.url()).map(arg -> link);
    }

    @Override
    public Mono<Link> findByRandomKey(String key) {
        return ops.opsForValue().get(key).map(str -> new Link(str, key));
    }
}
