package com.quizbattleroyale.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter implements WebFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Value("${jwt.secret:your-secret-key-change-this}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        
        // Bypass pour les routes publiques
        if (path.startsWith("/api/gateway/")) {
            return chain.filter(exchange);
        }

        try {
            String token = extractToken(exchange.getRequest().getHeaders().getFirst("Authorization"));
            
            if (token != null && validateToken(token)) {
                String userId = extractUserId(token);
                exchange.getAttributes().put("userId", userId);
                ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate()
                        .header("X-User-ID", userId)
                        .build())
                    .build();
                log.info("Token validé pour user: {}", userId);
                return chain.filter(mutatedExchange);
            } else {
                log.warn("Token invalide ou absent pour: {}", path);
                return onError(exchange, "Token invalide ou expiré", 401);
            }
        } catch (Exception e) {
            log.error("Erreur JWT: {}", e.getMessage());
            return onError(exchange, "Erreur d'authentification", 401);
        }
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

            return true;
        } catch (Exception e) {
            log.debug("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    private String extractUserId(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, int status) {
        exchange.getResponse().setStatusCode(HttpStatus.valueOf(status));
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorBody = String.format("{\"error\": \"%s\"}", message);
        return exchange.getResponse().writeWith(Mono.just(
            exchange.getResponse().bufferFactory().wrap(errorBody.getBytes())
        ));
    }
}
