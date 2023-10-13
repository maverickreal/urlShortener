package com.maverick.url_shortener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import com.maverick.url_shortener.data.repositories.ShortenerRepo;
import com.maverick.url_shortener.logic.services.LinkDto;
import com.maverick.url_shortener.logic.services.ShortenerService;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ShortenerserviceTest {
    private final static ShortenerRepo shortenerRepo = mock(ShortenerRepo.class);
    private final static ShortenerService shortenerService = new ShortenerService(shortenerRepo);

    @Before
    public void setup() {
        when(shortenerRepo.save(any())).thenAnswer(new Answer<Mono<LinkDto>>() {
            @Override
            public Mono<LinkDto> answer(InvocationOnMock invocation) throws Throwable {
                return Mono.just((LinkDto) invocation.getArguments()[0]);
            }
        });
    }

    @Test
    public void testShortenerService() {
        StepVerifier.create(shortenerService.getShortenedLink("http://spring.io"))
                .expectNextMatches(res -> res != null && res.length() > 0).expectComplete()
                .verify();
    }
}