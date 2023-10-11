package com.maverick.url_shortener;

import org.junit.Test;
import com.maverick.url_shortener.logic.services.ShortenerService;
import reactor.test.StepVerifier;

public class ShortenerserviceTest {
    private static final ShortenerService shortenerService = new ShortenerService();

    @Test
    public void testShortenerService() {
        StepVerifier.create(shortenerService.getShortenedLink("http://spring.io"))
                .expectNextMatches(res -> res != null && res.length() > 0).expectComplete()
                .verify();
    }
}