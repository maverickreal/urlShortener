package com.maverick.url_shortener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.maverick.url_shortener.data.repositories.RedisRepo;
import com.maverick.url_shortener.logic.services.LinkDto;
import reactor.test.StepVerifier;

@SpringBootTest()
@RunWith(SpringRunner.class)
public class RedisRepoTest {
    @Autowired
    private RedisRepo redisRepo;

    @Test
    public void returnArgLink() {
        LinkDto link = new LinkDto("http://start.spring.io", "xyz");
        StepVerifier.create(redisRepo.save(link)).expectNext(link).verifyComplete();
    }

    @Test
    public void save() {
        LinkDto link = new LinkDto("http://start.spring.io", "xyz");
        StepVerifier.create(redisRepo.save(link).flatMap(arg -> redisRepo.findByKey(link.key())))
                .expectNext(link)
                .verifyComplete();
    }
}