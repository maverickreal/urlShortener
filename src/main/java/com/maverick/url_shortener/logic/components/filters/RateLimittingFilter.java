package com.maverick.url_shortener.logic.components.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import com.maverick.url_shortener.logic.services.RateLimittingService;

@Component
public class RateLimittingFilter implements WebFilter {
    @Autowired
    private RateLimittingService RateLimittingService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            String ipaddress = exchange.getRequest().getRemoteAddress().toString();
            if (RateLimittingService.allowRequest(ipaddress)) {
                return chain.filter(exchange);
            }
        } catch (Exception err) {
            System.out.println("Error : " + err);
        }
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }
}