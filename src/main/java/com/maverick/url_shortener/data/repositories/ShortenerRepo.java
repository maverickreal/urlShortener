package com.maverick.url_shortener.data.repositories;

import com.maverick.url_shortener.logic.services.Link;
import reactor.core.publisher.Mono;

public interface ShortenerRepo {
    public Mono<Link> save(Link link);

    public Mono<Link> findByKey(String randomKey);
}