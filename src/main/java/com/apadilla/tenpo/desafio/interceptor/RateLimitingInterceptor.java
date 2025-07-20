package com.apadilla.tenpo.desafio.interceptor;

import com.apadilla.tenpo.desafio.exception.RateLimitExceededException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interceptor que limita la cantidad de peticiones por minuto.
 */
@Component
@Slf4j
public class RateLimitingInterceptor implements HandlerInterceptor {

    @Value("${app.interceptor.rateLimiting.rpm:3}")
    private int rpmLimit;

    private final ConcurrentHashMap<String, Bucket> bucketCache = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String clientIp = getClientIpAddress(request);
        Bucket bucket = getBucketForIp(clientIp);

        if (bucket.tryConsume(1)) {
            long remainingTokens = bucket.getAvailableTokens();
            response.setHeader("X-RateLimit-Limit", String.valueOf(rpmLimit));
            response.setHeader("X-RateLimit-Remaining", String.valueOf(remainingTokens));

            log.info("request allowed for IP: {} - remaining tokens: {}", clientIp, remainingTokens);
            return true;
        } else {
            log.error("rate limit exceeded for IP: {}", clientIp);
            response.setHeader("X-RateLimit-Limit", String.valueOf(rpmLimit));
            response.setHeader("X-RateLimit-Remaining", "0");
            response.setHeader("Retry-After", "60");

            throw new RateLimitExceededException(
                String.format("rate limit of %d requests per minute exceeded", rpmLimit)
            );
        }
    }

    private Bucket getBucketForIp(String clientIp) {
        return bucketCache.computeIfAbsent(clientIp, this::createNewBucket);
    }

    private Bucket createNewBucket(String clientIp) {
        log.info("Creating new bucket for IP: {} with RPM limit: {}", clientIp, rpmLimit);
        Bandwidth limit = Bandwidth.classic(rpmLimit, Refill.intervally(rpmLimit, Duration.ofMinutes(1)));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}
