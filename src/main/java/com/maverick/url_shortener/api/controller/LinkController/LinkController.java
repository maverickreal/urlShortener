package com.maverick.url_shortener.api.controller.LinkController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LinkController {
    @PostMapping("/link")
    Mono create(@RequestBody CreateLinkRequestBody body) {
        return Mono.just(new CreateLinkResponse("xyz"));
    }
}