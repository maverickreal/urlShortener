package com.maverick.url_shortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.maverick.url_shortener.data.repositories.RedisRepo;
import com.maverick.url_shortener.logic.services.Link;
import reactor.test.StepVerifier;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class RedisRepoTest {
    @Autowired
    private RedisRepo redisRepo;

    @Test
    public void returnArgLink() {
        Link link = new Link("http://bard.google.com", "dfa");
        StepVerifier.create(redisRepo.save(link)).expectNext(link).verifyComplete();
    }

    @Test
    public void save() {
        Link link = new Link("http://bard.google.com", "dfa");
        StepVerifier.create(redisRepo.save(link).flatMap(arg -> redisRepo.findByRandomKey(link.key())))
                .expectNext(link)
                .verifyComplete();
    }
}