package com.quizbattleroyale.controller;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/gateway")
public class GatewayController {
    private static final Logger log = LoggerFactory.getLogger(GatewayController.class);

    @GetMapping("/auth")
    public ResponseEntity<Map<String, Object>> authFallback() {
        log.warn("Auth service indisponible - fallback activé");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
            Map.of(
                "error", "Auth service temporairement indisponible",
                "status", 503,
                "timestamp", System.currentTimeMillis()
            )
        );
    }

    @GetMapping("/quiz")
    public ResponseEntity<Map<String, Object>> quizFallback() {
        log.warn("Quiz service indisponible - fallback activé");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
            Map.of(
                "error", "Quiz service temporairement indisponible",
                "status", 503,
                "timestamp", System.currentTimeMillis()
            )
        );
    }

    @GetMapping("/websocket")
    public ResponseEntity<Map<String, Object>> websocketFallback() {
        log.warn("WebSocket service indisponible - fallback activé");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(
            Map.of(
                "error", "WebSocket service temporairement indisponible",
                "status", 503,
                "timestamp", System.currentTimeMillis()
            )
        );
    }
}
