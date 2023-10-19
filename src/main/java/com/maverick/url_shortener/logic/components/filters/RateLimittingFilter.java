package com.maverick.url_shortener.logic.components.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import com.maverick.url_shortener.logic.services.RateLimittingService;
import reactor.core.publisher.Mono;

@Component
public class RateLimittingFilter implements WebFilter {
    @Autowired
    private RateLimittingService RateLimittingService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String ipaddress = exchange.getRequest().getRemoteAddress().toString();
        Mono<Boolean> allowed = RateLimittingService.allowRequest(ipaddress);
        return allowed.hasElement().flatMap(hasElement -> {
            if (hasElement) {
                return allowed.flatMap(consumed -> {
                    if (consumed) {
                        return chain.filter(exchange);
                    } else {
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        return exchange.getResponse().setComplete();
                    }
                });
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                return exchange.getResponse().setComplete();
            }
        });
    }
}

/*
 * @Component
 * public class RateLimittingFilter implements WebFilter {
 * 
 * @Autowired
 * private RateLimittingService RateLimittingService;
 * 
 * @Override
 * public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
 * String ipaddress = exchange.getRequest().getRemoteAddress().toString();
 * return RateLimittingService.allowRequest(ipaddress)
 * .flatMap(consumed -> {
 * if (consumed) {
 * return chain.filter(exchange);
 * } else {
 * exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
 * return exchange.getResponse().setComplete();
 * }
 * });
 * }
 * }
 */