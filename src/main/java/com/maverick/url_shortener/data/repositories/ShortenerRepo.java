package com.maverick.url_shortener.data.repositories;

import com.maverick.url_shortener.logic.services.LinkDto;
import reactor.core.publisher.Mono;

public interface ShortenerRepo {
    public Mono<LinkDto> save(LinkDto link);

    public Mono<LinkDto> findByKey(String randomKey);
}