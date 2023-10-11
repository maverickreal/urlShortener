package com.maverick.url_shortener.logic.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ShortenerService {
    public Mono<String> getShortenedLink(String longLink) {
        String randomKey = RandomStringUtils.randomAlphabetic(5);
        return Mono.just(randomKey);
    }
}