package com.quizbattleroyale.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class ObservationFilter implements WebFilter {
    private static final Logger log = LoggerFactory.getLogger(ObservationFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String traceId = UUID.randomUUID().toString();
        
        exchange.getAttributes().put("traceId", traceId);

        log.info("[{}] {} {} - User-Agent: {}", 
            traceId,
            exchange.getRequest().getMethod(), 
            exchange.getRequest().getPath(),
            exchange.getRequest().getHeaders().getFirst("User-Agent")
        );

        return chain.filter(exchange)
            .doFinally(signal -> {
                long duration = System.currentTimeMillis() - startTime;
                int status = exchange.getResponse().getStatusCode() != null ? 
                    exchange.getResponse().getStatusCode().value() : 0;
                
                log.info("[{}] Response: {} - Duration: {}ms", 
                    traceId, status, duration);
            });
    }
}
