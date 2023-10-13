package com.maverick.url_shortener.logic.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.maverick.url_shortener.data.repositories.ShortenerRepo;
import reactor.core.publisher.Mono;

@Service
public class ShortenerService {
    @Autowired
    private ShortenerRepo shortenerRepo;
    @Value("${shortenerService.baseUrl}")
    static private String baseUrl;

    public ShortenerService(ShortenerRepo _shortenerRepo) { // test
        shortenerRepo = _shortenerRepo;
    }

    public Mono<String> getShortenedLink(String longLink) {
        String randomKey = RandomStringUtils.randomAlphabetic(5);
        Link link = new Link(longLink, randomKey);
        return shortenerRepo.save(link)
                .map(savedLink -> baseUrl + savedLink.key());
    }

    public Mono<Link> getLink(String key) {
        return shortenerRepo.findByKey(key);
    }
}