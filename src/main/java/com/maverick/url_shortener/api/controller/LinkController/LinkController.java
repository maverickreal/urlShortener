package com.maverick.url_shortener.api.controller.LinkController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.maverick.url_shortener.logic.services.ShortenerService;
import reactor.core.publisher.Mono;

@RestController
public class LinkController {
    @Autowired
    private ShortenerService shortenerService;

    @PostMapping("/link")
    Mono<CreateLinkResponse> create(@RequestBody CreateLinkRequestBody body) {
        return shortenerService.getShortenedLink(body.link()).map(CreateLinkResponse::new);
    }
}