package com.quizbattleroyale.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class GatewayRedirectFilter implements WebFilter {
    private static final Logger log = LoggerFactory.getLogger(GatewayRedirectFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // If incoming path explicitly contains /api/gateway/, redirect to /api/ (remove gateway)
        String prefix = "/api/gateway/";
        if (path.startsWith(prefix)) {
            String newPath = "/api/" + path.substring(prefix.length());
            URI location = exchange.getRequest().getURI().resolve(newPath);
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
            response.getHeaders().setLocation(location);
            log.info("Redirecting {} -> {}", path, newPath);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }
}
