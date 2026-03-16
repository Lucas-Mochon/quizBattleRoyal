package com.quizbattleroyale.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AppLogger {
    private static final Logger log = LoggerFactory.getLogger(AppLogger.class);
    public void info(String message) {
        log.info("{}", message);
    }

    public void info(String message, Object... args) {
        log.info("{}", String.format(message, args));
    }

    public void debug(String message) {
        log.debug("{}", message);
    }

    public void debug(String message, Object... args) {
        log.debug("{}", String.format(message, args));
    }

    public void warn(String message) {
        log.warn("{}", message);
    }

    public void warn(String message, Object... args) {
        log.warn("{}", String.format(message, args));
    }

    public void error(String message) {
        log.error("{}", message);
    }

    public void error(String message, Throwable throwable) {
        log.error("{}", message, throwable);
    }

    public void error(String message, Object... args) {
        log.error("{}", String.format(message, args));
    }

    public void success(String message) {
        log.info("{}", message);
    }

    public void success(String message, Object... args) {
        log.info("{}", String.format(message, args));
    }

    public void request(String method, String path) {
        log.info("{} {}", method, path);
    }

    public void request(String method, String path, int statusCode) {
        log.info("{} {} -> {}", method, path, statusCode);
    }

    public void route(String routeId, String uri) {
        log.info("Route: {} -> {}", routeId, uri);
    }

    public void service(String serviceName, String action) {
        log.info("Service [{}] {}", serviceName, action);
    }

    public void database(String query) {
        log.debug("{}", query);
    }

    public void security(String message) {
        log.warn("{}", message);
    }

    public void performance(String operation, long durationMs) {
        log.info("{} took {}ms", operation, durationMs);
    }
}
