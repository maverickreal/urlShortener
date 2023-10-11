package com.maverick.url_shortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.maverick.url_shortener.api.controller.LinkController.LinkController;
import com.maverick.url_shortener.logic.services.ShortenerService;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = LinkController.class)
public class LinkControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ShortenerService shortenerService;

    @Test
    public void shortenLink() {
        when(shortenerService.getShortenedLink("http://spring.io")).thenReturn(Mono.just("xyz"));
        webTestClient.post()
                .uri("/link").contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"link\":\"http://spring.io\"}").exchange().expectStatus().is2xxSuccessful()
                .expectBody().jsonPath("$.shortenedLink")
                .value(val -> assertThat(val).isEqualTo("xyz"));
    }
}