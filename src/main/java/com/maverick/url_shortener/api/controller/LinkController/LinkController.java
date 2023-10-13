package com.maverick.url_shortener.api.controller.LinkController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.maverick.url_shortener.logic.services.ShortenerService;
import reactor.core.publisher.Mono;

@RestController
public class LinkController {
    @Autowired
    private ShortenerService shortenerService;

    @GetMapping
    public Mono<String> home() {
        return Mono.just("Hello World!");
    }

    @PostMapping
    Mono<LinkDto> create(@RequestBody LinkDto body) {
        return shortenerService.getShortenedLink(body.getLink()).map(LinkDto::new);
    }

    @GetMapping("{key}")
    public Mono<ResponseEntity<Object>> getLink(@PathVariable String key) {
        return shortenerService.getLink(key).map(
                link -> ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                        .header("Location", link.url()).build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}